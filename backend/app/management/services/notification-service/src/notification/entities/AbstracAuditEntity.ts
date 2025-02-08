import { Column, CreateDateColumn, UpdateDateColumn } from 'typeorm';

export abstract class AbstractAuditEntity {
  @CreateDateColumn()
  @Column('timestamp without time zone', { name: 'created_at' })
  createdAt: Date;

  @Column({ name: 'created_by', nullable: false })
  createdBy: string;

  @UpdateDateColumn()
  @Column('timestamp without time zone', { name: 'updated_at' })
  updatedAt: Date;

  @Column({ name: 'updated_by', nullable: false })
  updateBy: string;
}
