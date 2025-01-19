import { AbstractAuthFactory, OAuthService, TokenService, ValidationService } from './abstract-auth.factory';

export class TwitterAuthFactory extends AbstractAuthFactory {
  authService(): OAuthService {
    throw new Error('Method not implemented.');
  }
  tokenService(): TokenService {
    throw new Error('Method not implemented.');
  }
  validationService(): ValidationService {
    throw new Error('Method not implemented.');
  }
}
