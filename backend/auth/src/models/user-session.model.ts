import { Sequelize, DataTypes, Model } from 'sequelize';
import { UserSession } from '@/interfaces/user-session.interface';

export class UserSessionModel extends Model<UserSession> implements UserSession {
  id!: number;
  userId!: string;
  ip!: string;
  device!: string;
  geoLocation!: string;

  public readonly createdAt!: Date;
  public readonly updatedAt!: Date;
}

export default function (sequelize: Sequelize): typeof UserSessionModel {
  UserSessionModel.init(
    {
      id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
      },
      userId: {
        allowNull: false,
        type: DataTypes.STRING(45),
      },
      ip: {
        allowNull: false,
        unique: true,
        type: DataTypes.STRING(100),
      },
      device: {
        allowNull: false,
        type: DataTypes.STRING(45),
      },
      geoLocation: {
        allowNull: false,
        type: DataTypes.STRING(20),
      },
    },
    {
      tableName: 'user_session',
      modelName: 'UserSession',
      sequelize,
    },
  );

  return UserSessionModel;
}
