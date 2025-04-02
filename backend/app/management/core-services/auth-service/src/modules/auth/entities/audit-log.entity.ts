import { Column, CreateDateColumn, Entity, PrimaryGeneratedColumn } from 'typeorm';

@Entity('audit_logs')
export class AuditLog {
  @PrimaryGeneratedColumn('identity')
  id: string;

  @Column({ name: 'account_id' })
  accountId: string;

  @Column()
  action: string;

  @Column('json')
  details: Record<string, any>;

  @Column({ name: 'ip_address' })
  ipAddress: string;

  @Column({ name: 'user_agent' })
  userAgent: string;

  @Column()
  status: string;

  @CreateDateColumn({ name: 'created_at', nullable: false, update: false })
  createdAt: Date;
}
