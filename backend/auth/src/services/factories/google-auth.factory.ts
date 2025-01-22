import { GoogleOAuthStrategy } from '../strategies';
import { AuthStrategy } from '../strategies/auth-strategy.interface';
import { AbstractAuthFactory, TokenService, ValidationService } from './abstract-auth.factory';

export class GoogleAuthFactory extends AbstractAuthFactory {
  authService(): AuthStrategy {
    return new GoogleOAuthStrategy(this.tokenService(), this.validationService());
  }

  tokenService(): TokenService {
    return {
      generateAccessToken: () => {
        return null;
      },
      refreshAccessToken: async (refreshToken: string) => {
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

        const data = await response.json();
        return data.access_token;
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
      },
    };
  }
}
