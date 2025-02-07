import { Sequelize, DataTypes, Model } from 'sequelize';
import { Customer } from '@/interfaces/account.interface';
import { LoginType } from '@/constants/enum';

export class CustomerModel extends Model<Customer> implements Customer {
  accountId: string;
  name: string;
  phone: string;
  phone_verified: boolean;
  address: string;
  dob: Date;
  gender: string;
  image: string;
  id: number;
  email: string;
  password: string;
  provider: LoginType;

  public readonly createdAt!: Date;
  public readonly updatedAt!: Date;
}

export default function (sequelize: Sequelize): typeof CustomerModel {
  CustomerModel.init(
    {
      id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
      },
      email: {
        allowNull: false,
        type: DataTypes.STRING(45),
      },
      accountId: {
        allowNull: false,
        unique: true,
        type: DataTypes.UUID,
      },
      name: {
        allowNull: false,
        type: DataTypes.STRING(45),
      },
      phone: {
        allowNull: false,
        type: DataTypes.STRING(20),
      },
      phone_verified: {
        type: DataTypes.BOOLEAN,
        defaultValue: false,
      },
      address: {
        allowNull: false,
        type: DataTypes.STRING,
      },
      dob: {
        type: DataTypes.DATEONLY,
      },
      gender: {
        allowNull: false,
        type: DataTypes.STRING(10),
      },
      image: {
        allowNull: false,
        type: DataTypes.STRING,
      },
      provider: {
        allowNull: false,
        type: DataTypes.ENUM(typeof LoginType),
      },
    },
    {
      tableName: 'customer',
      modelName: 'CustomerModel',
      sequelize,
    },
  );

  return CustomerModel;
}
