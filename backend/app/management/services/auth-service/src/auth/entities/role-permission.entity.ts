import { Column, Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: 'role_permission' })
export class RolePermission {
  @PrimaryColumn('bigint')
  id: number;

  @Column('bigint', { name: 'role_id' })
  roleId: number;

  @Column('bigint', { name: 'permission_id' })
  permissionId: number;
}
