import { Model, Sequelize } from 'sequelize';
import { RoleModel } from './role.model';
import { UserModel } from './user.model';

export class UserRoleModel extends Model {}

export default function (sequelize: Sequelize): typeof UserRoleModel {
  UserRoleModel.init({}, { sequelize, modelName: 'UserRole' });
  // Associations
  UserModel.belongsToMany(RoleModel, { through: UserRoleModel });
  RoleModel.belongsToMany(UserModel, { through: UserRoleModel });

  return UserRoleModel;
}
