import { DataTypes, Model, Sequelize } from 'sequelize';
import { RolePermission } from '@/interfaces/role-permission.interface';
import { PermissionModel, RoleModel } from '@/models';

export class RolePermissionModel extends Model<RolePermission> implements RolePermission {
  roleId!: number;
  permissionId!: number;
}

export default function (sequelize: Sequelize): typeof RolePermissionModel {
  RolePermissionModel.init(
    {
      roleId: {
        type: DataTypes.INTEGER,
        // references: { model: RoleModel, key: 'id' },
      },
      permissionId: {
        type: DataTypes.INTEGER,
        // references: { model: PermissionModel, key: 'id' },
      },
    },
    {
      modelName: 'RolePermissionModel',
      tableName: 'role_permission',
      sequelize,
      timestamps: false,
    },
  );

  return RolePermissionModel;
}
