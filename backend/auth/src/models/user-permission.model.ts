import { Sequelize, DataTypes, Model } from 'sequelize';
import { UserPermission } from '@/interfaces/user-permission.interface';

export class UserPermissionModel extends Model<UserPermission> implements UserPermission {
  id: number;
  user: string;
  action: string;
  resource: string;
  created_at: Date;
  expired_at: Date;
}

export default function (sequelize: Sequelize): typeof UserPermissionModel {
  UserPermissionModel.init(
    {
      id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
      },
      user: {
        type: DataTypes.STRING(45),
        allowNull: false,
      },
      action: {
        type: DataTypes.STRING(45),
        allowNull: false,
      },
      resource: {
        type: DataTypes.STRING(45),
        allowNull: false,
      },
      created_at: {
        type: DataTypes.DATE,
        defaultValue: DataTypes.NOW(),
      },
      expired_at: {
        type: DataTypes.DATE,
      },
    },
    {
      sequelize,
      tableName: 'user_permission',
    },
  );
  return UserPermissionModel;
}
