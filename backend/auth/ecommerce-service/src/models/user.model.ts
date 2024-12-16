import { Model, DataTypes, Sequelize } from 'sequelize';
import { RoleModel } from './role.model';

export class UserModel extends Model {
  public id!: string;
  public email!: string;
  public password!: string;
  public name!: string;
  public roleId!: string; // Foreign key
}

export default function (sequelize: Sequelize): typeof UserModel {
  UserModel.init(
    {
      id: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true,
      },
      email: { type: DataTypes.STRING, unique: true, allowNull: false },
      password: { type: DataTypes.STRING, allowNull: false },
      name: { type: DataTypes.STRING },
    },
    { sequelize, modelName: 'User' },
  );
  return UserModel;
}

UserModel.belongsTo(RoleModel, { foreignKey: 'roleId', as: 'role' });
