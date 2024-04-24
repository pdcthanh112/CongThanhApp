import { OTP } from '@interfaces/otp.interface';
import { DataTypes, Model, Sequelize } from 'sequelize';

export class OTPModel extends Model<OTP> implements OTP {
  id: number;
  customerId: string;
  code: string;
  expiredAt: Date;
}

export default function (sequelize: Sequelize): typeof OTPModel {
  OTPModel.init(
    {
      id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
      },
      customerId: {
        type: DataTypes.STRING,
        allowNull: false,
      },
      code: {
        type: DataTypes.STRING(6),
        allowNull: false,
      },
      expiredAt: {
        type: DataTypes.STRING,
        allowNull: false,
        defaultValue: new Date(Date.now() + 5 * 60 * 1000),
      },
    },
    {
      tableName: 'OTP',
      sequelize,
    },
  );
  return OTPModel;
}
