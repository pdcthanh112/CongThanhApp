import { Column, Entity, PrimaryColumn } from 'typeorm';
import { AbstractAuditEntity } from './AbstracAuditEntity';

@Entity()
export class Notification extends AbstractAuditEntity{
  @PrimaryColumn()
  id: number;

  @Column()
  title: string;

  @Column('text')
  content: string;
  
}
