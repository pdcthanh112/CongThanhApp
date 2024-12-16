import { Sequelize, DataTypes, Model, Optional } from 'sequelize';
import { Employee } from '@interfaces/account.interface';

//export type UserCreationAttributes = Optional<Employee | 'id' | 'email' | 'password'>;

export class EmployeeModel extends Model<Employee> implements Employee{
  public id: number;
  public accountId: string;
  public empAccount: string;
  public email: string;
  public password: string;
  public name: string;
  public phone: string;
  public address: string;
  public department: string;
  public dob: Date;
  public gender: string;
  public image: string;
  public salary: number;
  public refreshToken?: string

  public readonly createdAt!: Date;
  public readonly updatedAt!: Date;
}

export default function (sequelize: Sequelize): typeof EmployeeModel {
  EmployeeModel.init(
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
      password: {
        allowNull: false,
        type: DataTypes.STRING(255),
      },
      accountId: {
        allowNull: false,
        unique: true,
        type: DataTypes.UUID,
      },
      empAccount: {
        allowNull: false,
        unique: true,
        type: DataTypes.STRING(45),
      },
      name: {
        allowNull: false,
        type: DataTypes.STRING(45),
      },
      phone: {
        allowNull: false,
        type: DataTypes.STRING(20),
      },
      address: {
        allowNull: false,
        type: DataTypes.STRING,
      },
      department: {
        allowNull: false,
        type: DataTypes.STRING(45),
      },
      dob: {
        type: DataTypes.DATEONLY,
      },
      gender: {
        allowNull: false,
        type: DataTypes.STRING(10),
      },
      image: {
        type: DataTypes.STRING,
      },
      salary: {
        allowNull: false,
        type: DataTypes.DOUBLE,
      },
      refreshToken: {
        type: DataTypes.STRING,
      }
    },
    {
      tableName: 'Employee',
      sequelize,
    },
  );

  return EmployeeModel;
}
