import { Sequelize, DataTypes, Model } from 'sequelize';
import { Role } from '@/interfaces/role.interface';

export class RoleModel extends Model<Role> implements Role {
  id!: number;
  name!: string;
  description!: string;

  public readonly createdAt!: Date;
  public readonly updatedAt!: Date;
}

export default function (sequelize: Sequelize): typeof RoleModel {
  RoleModel.init(
    {
      id: {
        type: DataTypes.BIGINT,
        primaryKey: true,
      },
      name: {
        type: DataTypes.STRING,
      },
      description: {
        type: DataTypes.STRING,
      },
    },
    {
      modelName: 'RoleModel',
      tableName: 'role',
      sequelize,
      timestamps: true,
    },
  );

  return RoleModel;
}
