import { AbstractAuthFactory, TokenService, ValidationService } from './abstract-auth.factory';
import { ACCESS_TOKEN_SECRET, REFRESH_TOKEN_SECRET } from '@/config/index';
import { MYSQL_DB } from '@/databases/mysql';
import { HttpException } from '@/exceptions';
import * as jwt from 'jsonwebtoken';
import { AuthStrategy } from '../strategies/auth-strategy.interface';
import { CredentialsOAuthStrategy } from '../strategies';

export class CredentialsAuthFactory extends AbstractAuthFactory {
  authService(): AuthStrategy {
    return new CredentialsOAuthStrategy(this.tokenService(), this.validationService());
  }

  tokenService(): TokenService {
    return {
      generateAccessToken: (payload: string) => {
        return jwt.sign({ userId: payload }, ACCESS_TOKEN_SECRET, { expiresIn: process.env.ACCESS_TOKEN_EXPIRED });
      },
      refreshAccessToken: async (refreshToken: string) => {
        const checkToken = await MYSQL_DB.RefreshToken.findOne({ where: { token: refreshToken } });
        if (checkToken) {
          try {
            if (Number(checkToken.expiresAt) < Date.now()) throw new HttpException(404, 'Refresh token expires', 101007);
            const user = jwt.verify(refreshToken, REFRESH_TOKEN_SECRET);
            return jwt.sign({ userId: user }, ACCESS_TOKEN_SECRET, { expiresIn: process.env.ACCESS_TOKEN_EXPIRED });
          } catch (error) {
            throw new HttpException(404, 'Error get refresh access token', error);
          }
        } else {
          throw new HttpException(404, 'Refresh token invalid', 101007);
        }
      },
      revokeToken: async (token: string) => {
        await fetch('https://oauth2.googleapis.com/revoke', { method: 'POST', body: token, headers: { 'Content-Type': 'application/json' } });
      },
    };
  }

  validationService(): ValidationService {
    return {
      validateCredentials: () => {
        return null;
      },
      validateToken: async (token: string) => {
        return !!token;
      },
    };
  }
}
