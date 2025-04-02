import { Column, Entity, PrimaryColumn } from 'typeorm';
import { AbstractAuditEntity } from './abstract-audit.entity';

@Entity({ name: 'permissions' })
export class Permission extends AbstractAuditEntity {
  @PrimaryColumn('bigint')
  id: number;

  @Column()
  action: string;

  @Column()
  resource: string;
}
