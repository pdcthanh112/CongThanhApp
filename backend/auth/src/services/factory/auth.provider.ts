import { LoginType } from '@/constants/enum';
import { FacebookOAuthFactory, GoogleOAuthFactory, OAuthFactory } from './oauth.factory';
import { CredentialsOAuthFactory } from './credentials.factory';

export class AuthProvider {
  static createAuthService(type: LoginType): OAuthFactory {
    switch (type) {
      case LoginType.CREDENTIALS:
        return new CredentialsOAuthFactory();
      case LoginType.GOOGLE:
        return new GoogleOAuthFactory();
    case LoginType.FACEBOOK:
        return new FacebookOAuthFactory()
      default:
        throw new Error('Invalid auth type');
    }
  }
}
