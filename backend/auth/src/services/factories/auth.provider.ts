import { LoginType } from '@/constants/enum';
import { GoogleAuthFactory } from './google-auth.factory';
import { AbstractAuthFactory } from './abstract-auth.factory';
import { FacebookAuthFactory } from './facebook-auth.factory';
import { CredentialsAuthFactory } from './credentials-auth.factory';

export class AuthProvider {
  private static factories = new Map<LoginType, () => AbstractAuthFactory>([
    [LoginType.CREDENTIALS, () => new CredentialsAuthFactory()],
    [LoginType.GOOGLE, () => new GoogleAuthFactory()],
    [LoginType.FACEBOOK, () => new FacebookAuthFactory()],
  ]);

  static getFactory(type: LoginType): AbstractAuthFactory {
    const factoryCreator = this.factories.get(type);
    if (!factoryCreator) {
      throw new Error(`Unsupported authentication type: ${type}`);
    }
    return factoryCreator();
  }

  static registerProvider(type: LoginType, factoryCreator: () => AbstractAuthFactory): void {
    this.factories.set(type, factoryCreator);
  }
}
