import { Sequelize, DataTypes, Model, Optional } from 'sequelize';
import { Customer } from '@interfaces/account.interface';

//export type UserCreationAttributes = Optional<Customer | 'id' | 'email' | 'password'>;

export class CustomerModel extends Model<Customer> implements Customer {
  accountId: string;
  name: string;
  phone: string;
  phone_verified: boolean;
  address: string;
  dob: Date;
  gender: string;
  image: string;
  public id: number;
  public email: string;
  public password: string;

  public readonly createdAt!: Date;
  public readonly updatedAt!: Date;
}

export default function (sequelize: Sequelize): typeof CustomerModel {
  CustomerModel.init({
      id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
      },
      email: {
        allowNull: false,
        type: DataTypes.STRING(45),
      },
      password: {
        allowNull: false,
        type: DataTypes.STRING(255),
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
        defaultValue: false
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
    },
    {
      tableName: 'Customer',
      sequelize,
    },
  );

  return CustomerModel;
}
