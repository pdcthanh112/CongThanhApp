import { CustomerSignupDTO, LoginGoogle } from '@/dtos/customer.dto';
import { AuthResponse, TokenData } from '@/interfaces/auth.interface';
import { MYSQL_DB } from '@/databases/mysql';
import { LoginType } from '@/constants/enum';
import { AuthService } from '../auth.service';
import { TokenService, ValidationService } from '../factories/abstract-auth.factory';
import { AuthStrategy } from './auth-strategy.interface';

export class GoogleOAuthStrategy implements AuthStrategy {
  constructor(
    private readonly tokenService: TokenService,
    private readonly validationService: ValidationService,
  ) {}

  async authenticate(loginData: LoginGoogle): Promise<AuthResponse> {
    const validateToken = await this.validationService.validateToken(loginData.accessToken);
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
        new AuthService().signup(signupData);
      }
      await MYSQL_DB.RefreshToken.create({ accountId: user.accountId, token: loginData.refreshToken, expiresAt: loginData.expires });
    }
    return null;
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
    console.log('RRRRRRRRRRRRRRRRRRRR', response);
    return response.json();
  }

  async validateToken(token: string): Promise<boolean> {
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
}
