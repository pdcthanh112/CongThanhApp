import { IsString, IsEmail, IsNotEmpty, MinLength, MaxLength, IsPhoneNumber, IsNumber, IsIn } from 'class-validator';
import { LoginType } from '@/constants/enum';

export type CustomerLoginDTO = LoginCredentials | LoginGoogle | LoginFacebook | LoginTwitter | LoginApple;
export class LoginCredentials {
  @IsEmail()
  email: string;

  @IsString()
  @IsNotEmpty()
  @MinLength(8)
  @MaxLength(32)
  password: string;

  @IsIn([LoginType.CREDENTIALS])
  provider: LoginType.CREDENTIALS;
}

class LoginGoogle {
  @IsEmail()
  email: string;

  @IsString()
  name: string;
  accessToken: string;
  refreshToken: string;
  expires: number;

  @IsIn([LoginType.GOOGLE])
  provider: LoginType.GOOGLE;
}
class LoginFacebook {
  @IsEmail()
  email: string;

  accessToken: string;

  @IsIn([LoginType.FACEBOOK])
  provider: LoginType.FACEBOOK;
}

class LoginTwitter {
  @IsEmail()
  email: string;

  @IsIn([LoginType.TWITTER])
  provider: LoginType.TWITTER;
}

class LoginApple {
  @IsEmail()
  email: string;

  @IsIn([LoginType.APPLE])
  provider: LoginType.APPLE;
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
  provider: LoginType
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
  salary: number;
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
