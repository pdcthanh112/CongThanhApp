export interface ResetPassword {
  id: number;
  email: string;
  token: string;
  createdAt: Date;
  isUsed: boolean;
}
