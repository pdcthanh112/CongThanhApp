import { Sequelize, DataTypes, Model, Optional } from 'sequelize';
import { Candidate } from '@interfaces/account.interface';

export type CandidateCreationAttributes = Optional<Candidate, 'id' | 'email' | 'password'>;

export class CandidateModel extends Model<Candidate, CandidateCreationAttributes> implements Candidate{
  accountId: string;
  name: string;
  phone: string;
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

export default function (sequelize: Sequelize): typeof CandidateModel {
  CandidateModel.init(
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
        type: DataTypes.UUID,
        allowNull: false,
        unique: true,
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
      tableName: 'Candidate',
      sequelize,
    },
  );

  return CandidateModel;
}