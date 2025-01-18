import { TokenPayload } from '@/interfaces/auth.interface';
import * as jwt from 'jsonwebtoken';

export function generateAccessToken(data: TokenPayload): string {
  const convertRole = data.role.split(',').map(item => item.trim());
  return jwt.sign({ accountId: data.accountId, role: convertRole }, process.env.ACCESS_TOKEN_SECRET, { expiresIn: process.env.ACCESS_TOKEN_EXPIRED });
}

export function generateRefreshToken(accountId: string) {
  const refreshToken = jwt.sign({ accountId }, process.env.REFRESH_TOKEN_SECRET, { expiresIn: process.env.REFRESH_TOKEN_EXPIRED });
  return refreshToken;
}
