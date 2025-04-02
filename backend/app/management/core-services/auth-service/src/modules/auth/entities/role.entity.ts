import { Column, Entity, PrimaryColumn } from 'typeorm';
import { AbstractAuditEntity } from './abstract-audit.entity';

@Entity({ name: 'roles' })
export class Role extends AbstractAuditEntity {
  @PrimaryColumn('bigint')
  id: number;

  @Column({ nullable: false, unique: true })
  name: string;
}
