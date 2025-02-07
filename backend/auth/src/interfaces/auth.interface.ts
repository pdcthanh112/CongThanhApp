import { Request } from 'express';
import { Account, Customer } from './account.interface';

export interface TokenPayload {
  accountId: string;
  role: string[];
  permission: string[]
}

export interface TokenData {
  accessToken: string;
  refreshToken: string;
}

export interface AuthResponse {
  user: Omit<Account, 'password'>;
  token: TokenData;
}

export interface LoginError {
  id: number;
  accountId: string;
  failedAttempts: number;
  lockedUntil: Date;
}

export interface RequestWithUser extends Request {
  user: Customer;
}
