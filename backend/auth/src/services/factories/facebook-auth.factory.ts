import { AbstractAuthFactory, OAuthService, TokenService, ValidationService } from './abstract-auth.factory';

export class FacebookAuthFactory extends AbstractAuthFactory {
  authService(): OAuthService {
    return new FacebookOAuthService();
  }

  tokenService(): TokenService {
    return new FacebookTokenService();
  }

  validationService(): ValidationService {
    return new FacebookValidationService();
  }
}
