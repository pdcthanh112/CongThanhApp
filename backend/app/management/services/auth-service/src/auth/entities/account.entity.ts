import { Column, Entity, PrimaryColumn } from 'typeorm';

@Entity({ name: 'accounts' })
export class Account {
  @PrimaryColumn('bigint')
  id: number;

  @Column({ name: 'account_id', nullable: false, unique: true })
  accountId: string;

  @Column({name: 'emp_email', nullable: false, unique: true})
  empEmail: string;

  @Column({ name: 'password_hash', nullable: false })
  password_hash: string;

  @Column()
  status: string;
}
