import { AbstractAuthFactory, OAuthService, TokenService, ValidationService } from './abstract-auth.factory';

export class GoogleAuthFactory extends AbstractAuthFactory {
  static tokenService = this.tokenService();
  static validationService = this.validationService();

  authService(): OAuthService {
    return {
      login: () => {
        return null;
      },
      logout() {
        return null;
      },
    };
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
      validateToken: () => {
        return null;
      },
    };
  }
}
