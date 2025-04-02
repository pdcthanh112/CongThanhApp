import { Column, CreateDateColumn, UpdateDateColumn } from 'typeorm';
import { AuditEntity } from '../../../common/interface/audit-entity.interface';

export abstract class AbstractAuditEntity implements AuditEntity {
  @CreateDateColumn({ name: 'created_at', update: false, default: () => 'CURRENT_TIMESTAMP', type: 'time without time zone' })
  createdAt: Date;

  @Column({ name: 'created_by', nullable: false, update: false })
  createdBy: string;

  @UpdateDateColumn({ name: 'updated_at', nullable: false, type: 'time without time zone' })
  updatedAt: Date;

  @Column({ name: 'updated_by', nullable: false })
  updatedBy: string;
}
