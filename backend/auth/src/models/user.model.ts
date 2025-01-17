import { Model, DataTypes, Sequelize } from 'sequelize';
import { User } from '@/interfaces/user.interface';
import { RoleModel } from './role.model';

export class UserModel extends Model<User> implements User {
  public id!: string;
  public email!: string;
  public password!: string;
  public name!: string;
  public roleId!: string;
}

export default function (sequelize: Sequelize): typeof UserModel {
  UserModel.init(
    {
      id: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true,
      },
    },
    {
      tableName: 'User',
      sequelize: sequelize,
    },
  );
  return UserModel;
}

// UserModel.belongsTo(RoleModel, { foreignKey: 'roleId', as: 'role' });
