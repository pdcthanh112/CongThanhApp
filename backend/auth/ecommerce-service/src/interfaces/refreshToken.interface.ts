export interface RefreshToken {
  id: number;
  accountId: string;
  token: string;
  expiresAt: Date | number;
  createdAt?: Date | number;
}
