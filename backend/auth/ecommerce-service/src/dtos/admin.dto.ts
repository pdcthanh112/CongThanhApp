import { UserRoleType } from '@/constants/enum';
import { IsString, IsEnum, IsUUID, IsNotEmpty } from 'class-validator';

export class SuspendUserDto {
  @IsUUID()
  userId: string;

  @IsString()
  @IsNotEmpty()
  reason: string;
}

export class AssignRoleDto {
  @IsUUID()
  userId: string;

  @IsEnum(UserRoleType)
  role: UserRoleType;
}
