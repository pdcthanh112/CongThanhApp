import { Permission } from './permission.interface';

export interface Role {
  id: number;
  name: string;
  description: string;
  permissions?: Permission[];
}
