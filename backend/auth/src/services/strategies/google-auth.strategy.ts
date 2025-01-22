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
    return { user: null, token: null };
  }

  async refreshAccessToken(refreshToken: string): Promise<TokenData> {
    return {
      accessToken: await this.tokenService.refreshAccessToken(refreshToken),
      refreshToken: refreshToken,
    };
  }

  async signOut(token: string): Promise<void> {
    const validate = await this.validationService.validateToken(token);
    if (!validate) {
      throw Error('token invalid');
    }
  }
}
