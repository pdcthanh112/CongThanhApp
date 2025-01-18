import { LoginType } from '@/constants/enum';
import { OAuthFactory } from './oauth.factory';
import { CredentialsOAuthFactory } from './credentials.factory';
import { GoogleOAuthFactory } from './google.factory';
import { FacebookOAuthFactory } from './facebook.factory';

export class AuthProvider {
  static createAuthService(type: LoginType): OAuthFactory {
    switch (type) {
      case LoginType.CREDENTIALS:
        return new CredentialsOAuthFactory();
      case LoginType.GOOGLE:
        return new GoogleOAuthFactory();
      case LoginType.FACEBOOK:
        return new FacebookOAuthFactory();
      default:
        throw new Error('Invalid auth type');
    }
  }
}
