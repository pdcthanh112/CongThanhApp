import { Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: 'user_roles' })
export class UserRole {
  @PrimaryColumn('bigint')
  id: number;

  accountId: number;

  roleId: number;
}
