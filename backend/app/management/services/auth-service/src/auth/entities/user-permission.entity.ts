import { Column, Entity, PrimaryColumn } from 'typeorm';
import { AbstractAuditEntity } from './abstract-audit.entity';

@Entity({ name: 'user_permission' })
export class UserPermission extends AbstractAuditEntity {
  @PrimaryColumn()
  id: number;

  @Column({ name: 'account_id', nullable: false })
  accountId: number;

  @Column({name: 'assign_by'})
  assignBy: string;

  @Column('time without time zone', { name: 'expired_at' })
  expiredAt: Date;
}
