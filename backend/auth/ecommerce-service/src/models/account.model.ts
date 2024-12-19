import { Sequelize, DataTypes, Model } from 'sequelize';
import { Account } from '@/interfaces/account.interface';

export class AccountModel extends Model<Account> implements Account{
  public id: number;
  public accountId: string;
  public email: string;
  public password: string;
  public role: string;

  public readonly createdAt!: Date;
  public readonly updatedAt!: Date;
}

export default function (sequelize: Sequelize): typeof AccountModel {
  AccountModel.init(
    {
      id: {
        type: DataTypes.INTEGER,
        autoIncrementIdentity: true,
        primaryKey: true,
      },
      accountId: {
        type: DataTypes.UUID,
        // defaultValue: DataTypes.UUIDV4,
        allowNull: false,
        unique: true
      },
      email: {
        allowNull: false,
        type: DataTypes.STRING(45),
      },
      password: {
        allowNull: false,
        type: DataTypes.STRING(255),
      },
      role: {
        type: DataTypes.STRING
      }
    },
    {
      tableName: 'Account',
      sequelize: sequelize,
      timestamps: true,
    },
  );

  return AccountModel;
}
