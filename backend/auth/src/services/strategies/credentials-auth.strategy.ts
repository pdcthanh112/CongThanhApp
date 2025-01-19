import { AuthStrategy } from './auth-strategy.interface';
import { LoginCredentials } from '@/dtos/customer.dto';
import * as bcrypt from 'bcrypt';
import { MYSQL_DB } from '@/databases/mysql';
import { HttpException } from '@/exceptions/';
import { AuthResponse, LoginError, TokenData } from '@/interfaces/auth.interface';
import { generateAccessToken, generateRefreshToken } from '@/utils/jwt.utils';

import { TokenService, ValidationService } from '../factories/abstract-auth.factory';

export class CredentialsOAuthStrategy implements AuthStrategy {
  constructor(
    private readonly tokenService: TokenService,
    private readonly validationService: ValidationService,
  ) {}

  async authenticate(loginData: LoginCredentials): Promise<AuthResponse> {
    const checkLogin = (await MYSQL_DB.Account.findOne({ where: { email: loginData.email } })).dataValues;

    if (!checkLogin) throw new HttpException(409, `This email ${loginData.email} was not found`, 101001);

    const findError: LoginError = await MYSQL_DB.LoginError.findOne({ where: { accountId: checkLogin.accountId } });

    if (findError && findError?.lockedUntil > new Date()) {
      throw new HttpException(409, `Your account have been blocked! (until ${findError.lockedUntil})`, 101003);
    } else {
      const isPasswordMatching: boolean = await bcrypt.compare(loginData.password, checkLogin.password);
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
        return { user: customerWithoutPassword, token: tokenData };
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

  async refreshAccessToken(refreshToken: string): Promise<TokenData> {
    const isValid = await this.validationService.validateToken(refreshToken);
    if (!isValid) {
      throw new HttpException(404, 'Invalid refresh token', 101007);
    }

    const newAccessToken = await this.tokenService.refreshAccessToken(refreshToken);
    return { accessToken: newAccessToken, refreshToken };
  }

  async validateToken(token: string): Promise<boolean> {
    return this.validationService.validateToken(token);
  }
}
