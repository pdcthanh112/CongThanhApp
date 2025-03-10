import { Sequelize, DataTypes, Model } from 'sequelize';
import { RefreshToken } from '@/interfaces/refreshToken.interface';

export class RefreshTokenModel extends Model<RefreshToken> implements RefreshToken {
  id!: number;
  accountId!: string;
  token!: string;
  expiresAt: number | Date;
  createdAt: number | Date;
}

export default function (sequelize: Sequelize): typeof RefreshTokenModel {
  RefreshTokenModel.init(
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
      token: {
        allowNull: false,
        type: DataTypes.STRING(255),
      },
      expiresAt: {
        allowNull: false,
        type: DataTypes.BIGINT,
      },
    },
    {
      tableName: 'refresh_token',
      modelName: 'RefreshTokenModel',
      sequelize,
    },
  );

  return RefreshTokenModel;
}
