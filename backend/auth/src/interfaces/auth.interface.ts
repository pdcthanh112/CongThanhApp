import { Request } from "express";
import { Customer } from "./account.interface";

export interface DataStoredInToken {
  accountId: string;
  role: string
}

export interface TokenData {
  accessToken: string;
  refreshToken: string;
  expiresIn: string | number
}

export interface LoginError {
  id:number;
  accountId: string;
  failedAttempts: number;
  lockedUntil: Date;
}

export interface RequestWithUser extends Request {
  user: Customer;
}
