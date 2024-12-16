import { Sequelize, DataTypes, Model } from 'sequelize';
import { LoginError } from '@interfaces/auth.interface';

export class LoginErrorModel extends Model<LoginError> implements LoginError {
  public id: number;
  public accountId: string;
  public failedAttempts: number;
  public lockedUntil: Date;

}

export default function (sequelize: Sequelize): typeof LoginErrorModel {
  LoginErrorModel.init(
    {
      id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
      },
      accountId: {
        allowNull: false,
        unique: true,
        type: DataTypes.UUID,
      },
      failedAttempts: {
        type: DataTypes.INTEGER,
      },
      lockedUntil: {
        type: DataTypes.DATE,
      },
    },
    {
      tableName: 'LoginError',
      timestamps: false,
      sequelize,
    },
  );

  return LoginErrorModel;
}
