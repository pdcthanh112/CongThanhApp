import { AuditEntity } from 'src/common/interfaces/audit-entity.interface';
import { Column, CreateDateColumn, UpdateDateColumn } from 'typeorm';

export abstract class AbstractAuditEntity implements AuditEntity {
  @CreateDateColumn({ name: 'created_at', update: false, type: 'time without time zone' })
  createdAt: Date;

  @Column({ name: 'created_by', nullable: false, update: false })
  createdBy: string;

  @UpdateDateColumn({ name: 'updated_at', type: 'timestamp without time zone' })
  updatedAt: Date;

  @Column({ name: 'updated_by', nullable: false })
  updatedBy: string;
}
