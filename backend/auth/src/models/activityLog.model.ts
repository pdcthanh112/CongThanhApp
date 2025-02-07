import { Sequelize, Model, DataTypes } from 'sequelize';
import { ActivityLog } from '@/interfaces/activityLog.interface';
import { ActivityType } from '@/constants/enum';

export class ActivityLogModel extends Model<ActivityLog> implements ActivityLog {
  id: number;
  accountId: string;
  activityType: ActivityType;
  ipAddress: string;
  createAt: Date;
}

export default function (sequelize: Sequelize): typeof ActivityLogModel {
  ActivityLogModel.init(
    {
      id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
      },
      accountId: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true,
      },
      activityType: {
        type: DataTypes.ENUM(typeof ActivityType),
        allowNull: false,
      },
      ipAddress: {
        type: DataTypes.STRING
      },
      createAt: {
        type: DataTypes.DATE,
        allowNull: false,
      },
    },
    {
      tableName: 'activity_log',
      sequelize,
    },
  );
  return ActivityLogModel;
}
