import { IsString, IsEmail, IsNotEmpty, MinLength, MaxLength, IsPhoneNumber, IsNumber, Matches } from 'class-validator';

export class CustomerLoginDTO {
  @IsEmail()
  email: string;

  @IsString()
  @IsNotEmpty()
  @MinLength(8)
  @MaxLength(32)
  password: string;
}

export class CustomerSignupDTO {
  @IsEmail()
  email: string;

  accountId?: string;

  name: string;

  @IsString()
  @IsNotEmpty()
  @MinLength(8)
  @MaxLength(32)
  password: string;

  @IsPhoneNumber()
  phone: string;

  address: string;
  dob?: Date;
  gender: string;
  image?: string;

}
export class UpdateCustomerDTO {
  name: string;

  @IsPhoneNumber()
  phone: string;

  address: string;
  department: string;
  dob: Date;
  gender: string;
  image: string;
  
  @IsNumber()
  salary: number

}

export class ChangePasswordDTO {

  @IsNotEmpty()
  customerId: string;

  @IsString()
  @IsNotEmpty()
  @MinLength(9)
  @MaxLength(32)
  currentPassword: string;

  @IsString()
  @IsNotEmpty()
  @MinLength(9)
  @MaxLength(32)
  newPassword: string;
}
