import { CustomerLoginDTO } from '@/dtos/customer.dto';
import { OAuthFactory } from './oauth.factory';

export class FacebookOAuthFactory implements OAuthFactory {
  async login(loginData: CustomerLoginDTO): Promise<any> {}

  async refreshAccessToken(refreshToken: string): Promise<unknown> {
    return refreshToken;
  }
  async validateToken(token: string): Promise<boolean> {
    return !!token;
    // ... similar implementation
  }
}
