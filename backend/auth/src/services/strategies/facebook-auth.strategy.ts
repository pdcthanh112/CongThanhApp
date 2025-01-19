import { LoginFacebook } from '@/dtos/customer.dto';
import { TokenService, ValidationService } from '../factories/abstract-auth.factory';
import { AuthResponse, TokenData } from '@/interfaces/auth.interface';
import { AuthStrategy } from './auth-strategy.interface';

export class FacebookOAuthStrategy implements AuthStrategy {
  constructor(
    private readonly tokenService: TokenService,
    private readonly validationService: ValidationService,
  ) {}

  async authenticate(loginData: LoginFacebook): Promise<AuthResponse> {
    const validateToken = this.validateToken(loginData.accessToken);
    if (validateToken) {
      console.log('first');
    }
  }

  async refreshAccessToken(refreshToken: string): Promise<TokenData> {
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

  async validateToken(token: string): Promise<boolean> {
    const APP_ID = process.env.FACEBOOK_CLIENT_ID!;
    const APP_SECRET = process.env.FACEBOOK_CLIENT_SECRET!;
    const APP_ACCESS_TOKEN = `${APP_ID}|${APP_SECRET}`;
    try {
      const response = await fetch(`https://graph.facebook.com/debug_token?input_token=${token}&access_token=${APP_ACCESS_TOKEN}`);

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
