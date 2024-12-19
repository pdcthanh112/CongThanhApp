import { compare, hash } from 'bcrypt';
import { sign, verify } from 'jsonwebtoken';
import { ACCESS_TOKEN_SECRET, REFRESH_TOKEN_SECRET } from '@/config/index';
import { MYSQL_DB } from '@/databases/mysql';
import { ChangePasswordDTO, CustomerLoginDTO, CustomerSignupDTO } from '@/dtos/customer.dto';
import { HttpException } from '@/exceptions/';
import { TokenPayload, LoginError, TokenData } from '@/interfaces/auth.interface';
import { Account, Customer } from '@/interfaces/account.interface';
import { v4 as uuidv4 } from 'uuid';
import sendEmail from '@/utils/sendEmail';
import sendSMS from '@/utils/sendSMS';
import { generateOTP } from '@/utils/helper';
import { OTP } from '@/interfaces/otp.interface';
import { ActivityType, LoginType } from '@/constants/enum';
import { sendActivityLogMessage } from '@/queue/producer/activity-log.producer';

function generateAccessToken(data: TokenPayload): string {
  const convertRole = data.role.split(',').map(item => item.trim());
  return sign({ accountId: data.accountId, role: convertRole }, process.env.ACCESS_TOKEN_SECRET, { expiresIn: process.env.ACCESS_TOKEN_EXPIRED });
}

function generateRefreshToken(accountId: string) {
  const refreshToken = sign({ accountId }, process.env.REFRESH_TOKEN_SECRET, { expiresIn: process.env.REFRESH_TOKEN_EXPIRED });
  return refreshToken;
}

export class AuthService {
  public async login(loginData: CustomerLoginDTO): Promise<{ customerWithoutPassword: Omit<Account, 'password'>; tokenData: TokenData }> {
    switch (loginData.provider) {
      case LoginType.CREDENTIALS:
        {
          const checkLogin = (await MYSQL_DB.Account.findOne({ where: { email: loginData.email } })).dataValues;

          if (!checkLogin) throw new HttpException(409, `This email ${loginData.email} was not found`, 101001);

          const findError: LoginError = await MYSQL_DB.LoginError.findOne({ where: { accountId: checkLogin.accountId } });

          if (findError && findError?.lockedUntil > new Date()) {
            throw new HttpException(409, `Your account have been blocked! (until ${findError.lockedUntil})`, 101003);
          } else {
            const isPasswordMatching: boolean = await compare(loginData.password, checkLogin.password);
            if (isPasswordMatching) {
              await MYSQL_DB.LoginError.destroy({ where: { accountId: checkLogin.accountId } });
              const customerInfo = await MYSQL_DB.Customer.findOne({ where: { accountId: checkLogin.accountId } });
              //cofn thieeus supplier nuawx
              checkLogin.customerInfo = customerInfo;
              checkLogin.supplierInfo = null;
              const { ...customerWithoutPassword } = checkLogin;

              const tokenData: TokenData = {
                accessToken: generateAccessToken({ accountId: checkLogin.accountId, role: checkLogin.role }),
                refreshToken: generateRefreshToken(checkLogin.accountId),
                // expiresIn: process.env.REFRESH_TOKEN_EXPIRED
              };

              const now = new Date();
              const expiredRefreshToken = now.setMonth(now.getMonth() + 1);
              await MYSQL_DB.RefreshToken.create({ accountId: checkLogin.accountId, token: tokenData.refreshToken, expiresAt: expiredRefreshToken });
              return { customerWithoutPassword, tokenData };
            } else {
              await MYSQL_DB.LoginError.upsert({
                accountId: checkLogin.accountId,
                failedAttempts: findError ? findError.failedAttempts + 1 : 1,
              });

              switch (true) {
                case findError === null || findError.failedAttempts < 4: {
                  throw new HttpException(409, 'Password is incorrect', 101002);
                }
                case findError.failedAttempts == 4: {
                  const lockDuration = 30 * 60 * 1000;
                  const lockedUntil = new Date(Date.now() + lockDuration);
                  await MYSQL_DB.LoginError.update({ lockedUntil: lockedUntil }, { where: { id: findError.id } });
                  throw new HttpException(409, `Your account have been blocked 30 mins! (until ${lockedUntil})`, 101003);
                }
                case findError.failedAttempts == 5: {
                  const lockDuration = 6 * 60 * 60 * 1000;
                  const lockedUntil = new Date(Date.now() + lockDuration);
                  await MYSQL_DB.LoginError.update({ lockedUntil: lockedUntil }, { where: { id: findError.id } });
                  throw new HttpException(409, `Your account have been blocked 6 hours! (until ${lockedUntil})`, 101003);
                }
                case findError.failedAttempts >= 6: {
                  const lockDuration = 24 * 60 * 60 * 1000;
                  const lockedUntil = new Date(Date.now() + lockDuration);
                  await MYSQL_DB.LoginError.update({ lockedUntil: lockedUntil }, { where: { id: findError.id } });
                  throw new HttpException(409, `Your account have been blocked 24 hours! (until ${lockedUntil})`, 101003);
                }
              }
            }
          }
        }
        break;
      case LoginType.GOOGLE:
        {
          const validateToken = this.validateGoogleToken(loginData.accessToken);
          if (validateToken) {
            const user = await MYSQL_DB.Account.findOne({ where: { email: loginData.email } });
            if (!user) {
              const signupData: CustomerSignupDTO = {
                email: loginData.email,
                name: loginData.name,
                password: null,
                phone: null,
                address: null,
                gender: null,
                provider: LoginType.GOOGLE,
              };
              this.signup(signupData);
            }
            await MYSQL_DB.RefreshToken.create({ accountId: user.accountId, token: loginData.refreshToken, expiresAt: loginData.expires });
          }
        }
        break;
      case LoginType.FACEBOOK:
        {
          const validateToken = this.validateFacebookToken(loginData.accessToken);
          if (validateToken) {
            console.log('first');
          }
        }
        break;
      case LoginType.TWITTER:
        {
          console.log('first');
        }
        break;
      case LoginType.APPLE:
        {
          console.log('first');
        }
        break;
      default:
        break;
    }

    sendActivityLogMessage({
      accountId: loginData.email,
      activityType: ActivityType.LOGIN,
      createAt: new Date(),
      ipAddress: '127.0.0.1',
      id: 0
    });
  }

  public async signup(customerData: CustomerSignupDTO): Promise<Customer> {
    const findCustomer = await MYSQL_DB.Customer.findOne({ where: { email: customerData.email } });
    if (findCustomer) throw new HttpException(409, `This email ${customerData.email} already exists`, 101004);

    const hashedPassword = await hash(customerData.password, 10);

    customerData.accountId = uuidv4();
    customerData.password = hashedPassword;
    const createCustomerData: Customer = await MYSQL_DB.Customer.create({ ...customerData });
    //sync data to table account
    await MYSQL_DB.Account.create({ accountId: customerData.accountId, email: customerData.email, password: hashedPassword, role: 'CUSTOMER' });
    return createCustomerData;
  }

  public async logout(customerData: Customer): Promise<Omit<Customer, 'password'>> {
    const findCustomer: Customer = await MYSQL_DB.Customer.findOne({ where: { email: customerData.email } });

    if (!findCustomer) throw new HttpException(409, `This email ${customerData.email} does not exists`, 101001);
    await MYSQL_DB.RefreshToken.destroy({ where: { $accountId$: customerData.accountId } });
    return findCustomer;
  }

  public async changePassword(data: ChangePasswordDTO): Promise<unknown> {
    const findCustomer: Customer = await MYSQL_DB.Customer.findOne({ where: { accountId: data.customerId } });

    if (!findCustomer) throw new HttpException(409, `This account does not exists`, 101001);

    const hashCurrentPassword = await hash(data.currentPassword, 10);
    if (findCustomer.password !== hashCurrentPassword) throw new HttpException(409, `Password does not match`, 101001);

    const hashNewPassword = await hash(data.newPassword, 10);
    return await MYSQL_DB.Customer.update({ password: hashNewPassword }, { where: { accountId: data.customerId } });
  }

  public async forgetPassword(data: { email: string }): Promise<unknown> {
    const findCustomer: Customer = await MYSQL_DB.Customer.findOne({ where: { email: data.email } });

    if (!findCustomer) throw new HttpException(409, `This email does not exists`, 101001);

    const resetPassworrdUrl = 'http://localhost:3000/auth/reset-password';
    // const resetPassworrdToken = '';

    const mailOptions = {
      email: findCustomer.email,
      subject: "CongThanh's Ecommerce App - Reset Password",
      content: `<!DOCTYPE html>
      <html lang="vi">
     
      <head>
          <meta char
          set="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <style>
              body {
                  font-family: Arial, sans-serif;
                  margin: 0;
                  padding: 0;
              }
      
              .header {
                  padding: 20px;
                  text-align: center;
              }
      
              .logo {
                  display: block;
                  margin: 0 auto;
                  width: 100px;
              }
      
              .content {
                  padding: 20px;
              }
      
              .content p {
                  font-size: 16px;
                  line-height: 1.5;
              }
      
              .content a {
                  color: #000;
                  text-decoration: underline;
              }
      
              .content a:hover {
                  color: #dae509;
                  text-decoration: underline;
      
              }
      
              .footer {
                  padding: 20px;
                  text-align: center;
              }
      
              .footer p {
                  font-size: 14px;
                  color: #666;
              }
          </style>
      </head>
      
      <body>
      
          <div class="content">
              <p>Bạn đang nhận được email này vì bạn đã yêu cầu đặt lại mật khẩu cho tài khoản của bạn tại [Tên công ty].</p>
              <p>Vui lòng nhấp vào liên kết dưới đây để đặt lại mật khẩu của bạn:</p>
              <p><a href="${resetPassworrdUrl}">Đặt lại mật khẩu</a></p>
              <p>Liên kết này sẽ hết hạn trong 24 giờ.</p>
              <p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>
          </div>
          <div class="footer">
              <p>Trân trọng,</p>
      
              <img src="https://firebasestorage.googleapis.com/v0/b/congthanh-project.appspot.com/o/ecommerce%2Fcustomer%2Fwallpaper.jpg?alt=media&token=09e60c5b-d8b0-42ff-9ea3-719246ff1eab"
                  alt="Logo [Tên công ty]" class="logo">
              <h4>CongThanh's Ecommerce App</h4>
          </div>
      </body>
      
      </html>`,
    };

    try {
      await sendEmail(mailOptions.email, mailOptions.subject, mailOptions.content);

      return { message: 'Email sent successfully' };
    } catch (error) {
      console.error(error);
      throw new HttpException(500, 'Failed to send email', 101005);
    }
  }

  public async generateOTP(data: { phone: string }): Promise<OTP> {
    const otp = generateOTP();
    const result: OTP = await MYSQL_DB.OTP.create({ code: otp });
    const content = `Your OTP is ${otp}, please don't share OTP for other people. This code will expire in 5 minutes.`;
    await sendSMS(content, data.phone)
      .then(response => response)
      .catch(error => {
        throw error;
      });
    return result;
  }

  public async verifyOTP(data: { code: string }): Promise<unknown> {
    const otp = await MYSQL_DB.OTP.findOne({ where: { code: data.code } });

    if (!otp || otp.expiredAt < new Date()) {
      throw new HttpException(409, 'Invalid OTP or expired', 101006);
    }

    if (otp.code !== data.code) {
      throw new HttpException(409, 'Incorrect OTP', 101007);
    }

    await MYSQL_DB.OTP.destroy({ where: { code: data.code } });

    return { message: 'OTP verified successfully' };
  }

  public async refreshAccessToken(refreshToken: string, provider: string): Promise<unknown> {
    const checkToken = await MYSQL_DB.RefreshToken.findOne({ where: { token: refreshToken } });
    if (checkToken) {
      try {
        if (Number(checkToken.expiresAt) < Date.now()) throw new HttpException(404, 'Refresh token expires', 101007);
        let newTokens = '';
        switch (provider) {
          case 'google':
            newTokens = await this.refreshGoogleAccessToken(refreshToken);
            break;
          case 'facebook':
            newTokens = await this.refreshFacebookAccessToken(refreshToken);
            break;
          case 'credentials':
            const user = verify(refreshToken, REFRESH_TOKEN_SECRET);
            newTokens = sign({ userId: user }, ACCESS_TOKEN_SECRET, { expiresIn: process.env.ACCESS_TOKEN_EXPIRED });
            break;
        }
        return newTokens;
      } catch (error) {
        throw new HttpException(404, 'Error get refresh access token', error);
      }
    } else {
      throw new HttpException(404, 'Refresh token invalid', 101007);
    }
  }

  private async refreshGoogleAccessToken(refreshToken: string) {
    const response = await fetch('https://oauth2.googleapis.com/token', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        client_id: process.env.GOOGLE_CLIENT_ID,
        client_secret: process.env.GOOGLE_CLIENT_SECRET,
        refresh_token: refreshToken,
        grant_type: 'refresh_token',
      }),
    });
    console.log('RRRRRRRRRRRRRRRRRRRR', response);
    return response.json();
  }

  private async refreshFacebookAccessToken(refreshToken: string) {
    const response = await fetch('https://oauth2.googleapis.com/token', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        client_id: process.env.GOOGLE_CLIENT_ID,
        client_secret: process.env.GOOGLE_CLIENT_SECRET,
        refresh_token: refreshToken,
        grant_type: 'refresh_token',
      }),
    });
    return response.json();
  }

  private async validateGoogleToken(token: string): Promise<boolean> {
    try {
      const response = await fetch(`https://oauth2.googleapis.com/tokeninfo?id_token=${token}`);
      if (!response.ok) {
        throw new Error('Invalid token');
      }

      const data = await response.json();
      // Kiểm tra thêm các thông tin nếu cần
      console.log('Token data:', data);

      return true;
    } catch (error) {
      console.error('Error validating Google token:', error);
      return false;
    }
  }

  private async validateFacebookToken(userToken: string): Promise<boolean> {
    const APP_ID = process.env.FACEBOOK_CLIENT_ID!;
    const APP_SECRET = process.env.FACEBOOK_CLIENT_SECRET!;
    const APP_ACCESS_TOKEN = `${APP_ID}|${APP_SECRET}`;
    try {
      const response = await fetch(`https://graph.facebook.com/debug_token?input_token=${userToken}&access_token=${APP_ACCESS_TOKEN}`);

      if (!response.ok) {
        throw new Error('Invalid token');
      }

      const data = await response.json();
      console.log('Token data:', data);

      // Kiểm tra thêm các thông tin nếu cần, ví dụ: app_id, user_id
      if (data.data && data.data.is_valid) {
        return true;
      }

      return false;
    } catch (error) {
      console.error('Error validating Facebook token:', error);
      return false;
    }
  }
}
