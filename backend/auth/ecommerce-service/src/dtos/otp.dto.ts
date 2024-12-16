import { IsNotEmpty, IsNumber, IsString } from 'class-validator';

export class OTPDTO {
  @IsNotEmpty()
  @IsNumber()
  public id: number;

  @IsString()
  @IsNotEmpty()
  public customerId: string;

  @IsString()
  @IsNotEmpty()
  public code: string;

  @IsNotEmpty()
  public expiredAt: Date;
}
