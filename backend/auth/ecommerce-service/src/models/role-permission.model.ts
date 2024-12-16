import { Model, Sequelize } from 'sequelize';
import { RoleModel } from './role.model';
import { PermissionModel } from './permission.model';

export class RolePermissionModel extends Model {}

export default function (sequelize: Sequelize): typeof RolePermissionModel {
  RolePermissionModel.init({}, { sequelize, modelName: 'RolePermission' });
  // Associations
  RoleModel.belongsToMany(PermissionModel, { through: RolePermissionModel });
  PermissionModel.belongsToMany(RoleModel, { through: RolePermissionModel });

  return RolePermissionModel;
}
