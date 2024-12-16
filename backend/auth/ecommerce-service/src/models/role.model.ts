import { Sequelize, DataTypes, Model } from 'sequelize';
import { Role } from '@/interfaces/role.interface';
import { UserRoleType } from '@/constants/enum';

export class RoleModel extends Model<Role> implements Role {
  id: string;
  name: string;
  type: UserRoleType;
  description: string;

  public readonly createdAt!: Date;
  public readonly updatedAt!: Date;
}

export default function (sequelize: Sequelize): typeof RoleModel {
  RoleModel.init(
    {
      id: {
        type: DataTypes.UUIDV4,
        primaryKey: true,
      },
      name: {
        type: DataTypes.STRING,
      },
      description: {
        type: DataTypes.STRING,
      },
      type: {
        type: DataTypes.ENUM(...Object.values(UserRoleType)),
        allowNull: false,
        unique: true,
      },
    },
    {
      tableName: 'Role',
      sequelize,
    },
  );

  return RoleModel;
}
