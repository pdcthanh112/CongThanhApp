import { IsString, IsEnum, IsUUID, IsNotEmpty } from 'class-validator';
import { UserRole } from '@/interfaces/role.interface';

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

  @IsEnum(UserRole)
  role: UserRole;
}
