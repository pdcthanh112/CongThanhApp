import { UserRoleType } from '@/constants/enum';
import { Permission } from './permission.interface';

export interface Role {
  id: string;
  name: string;
  type: UserRoleType;
  description: string;
  permissions?: Permission[];
}
