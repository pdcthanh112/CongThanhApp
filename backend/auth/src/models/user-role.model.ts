import { DataTypes, Model, Sequelize } from 'sequelize';
import { UserRole } from '@/interfaces/user-role.interface';
import { RoleModel, UserModel } from '@/models';

export class UserRoleModel extends Model<UserRole> implements UserRole {
  user: string;
  roleId: number;
}

export default function (sequelize: Sequelize): typeof UserRoleModel {
  UserRoleModel.init(
    {
      user: {
        type: DataTypes.STRING(45),
        allowNull: false,
        // references: { model: UserModel, key: "id" },
      },
      roleId: {
        type: DataTypes.INTEGER,
        allowNull: false,
        // references: { model: RoleModel, key: "id" },
      },
    },
    {
      sequelize,
      modelName: 'UserRole',
      tableName: 'user_role',
      timestamps: true,
    },
  );

  return UserRoleModel;
}
