import { LoginApple } from '@/dtos/customer.dto';
import { Account } from '@/interfaces/account.interface';
import { TokenData } from '@/interfaces/auth.interface';
import { OAuthFactory } from './oauth.factory';

export class AppleOAuthFactory implements OAuthFactory {
  async login(loginData: LoginApple): Promise<{ customerWithoutPassword: Omit<Account, 'password'>; tokenData: TokenData }> {
    throw new Error(loginData.email);
  }

  async refreshAccessToken(refreshToken: string): Promise<unknown> {
    throw new Error(refreshToken);
  }

  async validateToken(token: string): Promise<boolean> {
    throw new Error(token);
  }
}
