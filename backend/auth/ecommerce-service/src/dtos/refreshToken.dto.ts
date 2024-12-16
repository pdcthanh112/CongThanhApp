import { IsString } from "class-validator";

export class RefreshTokenDTO { 
  id: number;
  accountId: string;

  @IsString()
  token: string;
  expiresAt: number | Date;
  createdAt: number | Date;
}
