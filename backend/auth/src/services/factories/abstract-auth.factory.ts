// import { CustomerLoginDTO } from '@/dtos/customer.dto';
// import { AuthResponse } from '@/interfaces/auth.interface';
import { AuthStrategy } from '../strategies/auth-strategy.interface';

export abstract class AbstractAuthFactory {
  abstract authService(): AuthStrategy;
  abstract tokenService(): TokenService;
  abstract validationService(): ValidationService;
}

// export interface OAuthService {
//   login(credentials: CustomerLoginDTO): Promise<AuthResponse>;
//   logout(userId: string): Promise<void>;
// }

export interface TokenService {
  generateAccessToken(payload: unknown): string;
  refreshAccessToken(refreshToken: string): Promise<string>;
  // validateToken(token: string): unknown;
  revokeToken(token: string): Promise<void>;
}

export interface ValidationService {
  validateCredentials(credentials: unknown): Promise<boolean>;
  validateToken(token: string): Promise<boolean>;
}
