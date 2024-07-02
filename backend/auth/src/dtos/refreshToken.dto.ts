import { IsNumber } from "class-validator";

export class RefreshTokenDTO { 
  id: number;
  accountId: string;
  token: string;
  expiresAt: number | Date;
  createdAt: number | Date;
}
