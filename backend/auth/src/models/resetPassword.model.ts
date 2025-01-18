import { Sequelize, DataTypes, Model } from 'sequelize';
import { ResetPassword } from '@/interfaces/resetPassword.interface';

export class ResetPasswordModel extends Model<ResetPassword> implements ResetPassword {
  id: number;
  email: string;
  token: string;
  createdAt: Date;
  isUsed: boolean;
}

export default function (sequelize: Sequelize): typeof ResetPasswordModel {
  ResetPasswordModel.init(
    {
      id: {
        type: DataTypes.INTEGER,
        autoIncrementIdentity: true,
        primaryKey: true,
      },
      email: {
        allowNull: false,
        type: DataTypes.STRING(45),
      },
      token: {
        allowNull: false,
        type: DataTypes.STRING,
      },
      createdAt: {
        allowNull: false,
        type: DataTypes.DATE,
        defaultValue: DataTypes.DATE(),
      },
      isUsed: {
        allowNull: false,
        type: DataTypes.BOOLEAN,
        defaultValue: false,
      },
    },
    { tableName: 'reset_password', sequelize: sequelize, timestamps: true },
  );
  
  return ResetPasswordModel;
}
