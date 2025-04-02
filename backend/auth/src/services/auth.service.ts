import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';
import { MYSQL_DB } from '@/databases/mysql';
import { ChangePasswordDTO, CustomerLoginDTO, CustomerSignupDTO } from '@/dtos/customer.dto';
import { HttpException, TokenError } from '@/exceptions/';
import { AuthResponse } from '@/interfaces/auth.interface';
import { Customer } from '@/interfaces/account.interface';
import { v4 as uuidv4 } from 'uuid';
import sendEmail from '@/utils/sendEmail';
import sendSMS from '@/utils/sendSMS';
import { generateOTP } from '@/utils/helper';
import { OTP } from '@/interfaces/otp.interface';
import { ActivityType, LoginType } from '@/constants/enum';
import { sendActivityLogMessage } from '@/queue/producer/activity-log.producer';
import { AuthProvider } from './factories/auth.provider';

export class AuthService {
  
  public async login(loginData: CustomerLoginDTO): Promise<AuthResponse> {
    const factory = AuthProvider.getFactory(loginData.provider);
    const strategy = factory.authService()
    
    if (!strategy) {
      throw new Error('Invalid auth provider');
    }

    sendActivityLogMessage({
      accountId: loginData.email,
      activityType: ActivityType.LOGIN,
      createAt: new Date(),
      ipAddress: '127.0.0.1',
      id: 0,
    });

    return strategy.authenticate(loginData);
  }

  public async signup(customerData: CustomerSignupDTO): Promise<Customer> {
    const findCustomer = await MYSQL_DB.Customer.findOne({ where: { email: customerData.email } });
    if (findCustomer) throw new HttpException(409, `This email ${customerData.email} already exists`, 101004);

    const hashedPassword = await bcrypt.hash(customerData.password, 10);

    customerData.accountId = uuidv4();
    customerData.password = hashedPassword;
    const createCustomerData: Customer = await MYSQL_DB.Customer.create({ ...customerData });
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

    const hashCurrentPassword = await bcrypt.hash(data.currentPassword, 10);
    if (findCustomer.password !== hashCurrentPassword) throw new HttpException(409, `Password does not match`, 101001);

    const hashNewPassword = await bcrypt.hash(data.newPassword, 10);
    return await MYSQL_DB.Customer.update({ password: hashNewPassword }, { where: { accountId: data.customerId } });
  }

  public async forgetPassword(data: { email: string }): Promise<unknown> {
    const findCustomer: Customer = await MYSQL_DB.Customer.findOne({ where: { email: data.email } });

    if (!findCustomer) throw new HttpException(409, `This email does not exists`, 101001);

    const resetPassworrdToken = jwt.sign({ email: data.email, type: 'reset_password' }, process.env.JWT_SECRET, { expiresIn: '30m' });
    const resetPassworrdUrl = `http://localhost:3000/auth/reset-password?token=${resetPassworrdToken}`;

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

  public async resetPassword({ token, newPassword }: { token: string; newPassword: string }): Promise<unknown> {
    try {
      const decoded: any = jwt.verify(token, process.env.RESET_TOKEN_SECRET);
      const resetTokenRecord = await MYSQL_DB.ResetPassword.findOne({
        where: {
          email: decoded.email,
          token,
          isUsed: false,
        },
      });

      if (!resetTokenRecord) {
        throw new HttpException(400, 'Invalid or expired reset token', 101008);
      }

      const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,32}$/;
      if (!passwordRegex.test(newPassword)) {
        throw new HttpException(400, 'Password does not meet requirements', 101009);
      }

      const hashedPassword = await bcrypt.hash(newPassword, 10);

      await Promise.all([
        MYSQL_DB.Account.update({ password: hashedPassword }, { where: { email: decoded.email } }),
        MYSQL_DB.ResetPassword.update({ isUsed: true }, { where: { id: resetTokenRecord.id } }),
      ]);

      const account = await MYSQL_DB.Account.findOne({
        where: { email: decoded.email },
      });
      if (account) {
        await MYSQL_DB.RefreshToken.destroy({
          where: { accountId: account.accountId },
        });
      }

      return {
        status: 'success',
        message: 'Password has been reset successfully',
      };
    } catch (error) {
      throw new HttpException(500, `An error ${error} occurred while resetting your password`, 101010);
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

  public async refreshAccessToken(refreshToken: string, provider: LoginType): Promise<unknown> {
    const strategy = AuthProvider.getFactory(provider)
    if (!strategy) {
      throw new TokenError('INVALID_PROVIDER', `Unsupported provider: ${provider}`);
    }

    return strategy.tokenService().refreshAccessToken(refreshToken);
  }
}
