import { Sequelize, DataTypes, Model } from 'sequelize';
import { Permission } from '@/interfaces/permission.interface';

export class PermissionModel extends Model<Permission> implements Permission {
  id: string;
  name: string;
  description: string;
  action: string;
  resource: string;

  public readonly createdAt!: Date;
  public readonly updatedAt!: Date;
}

export default function (sequelize: Sequelize): typeof PermissionModel {
  PermissionModel.init(
    {
      id: {
        type: DataTypes.INTEGER,
        autoIncrementIdentity: true,
        primaryKey: true,
      },
      name: {
        allowNull: false,
        type: DataTypes.STRING(45),
      },
      description: {
        allowNull: false,
        type: DataTypes.TEXT,
      },
      action: {
        type: DataTypes.STRING,
      },
      resource: {
        type: DataTypes.STRING,
      },
    },
    {
      tableName: 'permission',
      modelName: 'PermissionModel',
      sequelize,
    },
  );

  return PermissionModel;
}
