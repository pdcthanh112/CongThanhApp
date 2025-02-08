import { Column, Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: 'departments' })
export class Department {
  @PrimaryColumn('bigint')
  id: number;

  @Column('text', { nullable: false })
  name: string;

  @Column('bigint', { name: 'manager_id' })
  managerId: number;
}
