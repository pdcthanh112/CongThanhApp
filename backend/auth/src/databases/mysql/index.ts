import Sequelize from 'sequelize';
import { NODE_ENV, MYSQL_HOST, MYSQL_PORT, MYSQL_USER, MYSQL_PASSWORD, MYSQL_DATABASE } from '@/config/index';
import { logger } from '@/utils/logger';
import {
  AccountModel,
  ActivityLogModel,
  CustomerModel,
  LoginErrorModel,
  OTPModel,
  RefreshTokenModel,
  UserModel,
  ResetPasswordModel,
  UserPermissionModel,
  PermissionModel,
  RolePermissionModel,
  UserRoleModel,
  RoleModel,
} from '@/models';

export const mysqlConnection = new Sequelize.Sequelize(MYSQL_DATABASE, MYSQL_USER, MYSQL_PASSWORD, {
  dialect: 'mysql',
  host: MYSQL_HOST,
  port: Number(MYSQL_PORT),
  timezone: '+07:00',
  define: {
    charset: 'utf8mb4',
    collate: 'utf8mb4_general_ci',
    underscored: true,
    freezeTableName: true,
  },
  pool: {
    min: 0,
    max: 5,
    acquire: 30000,
    idle: 10000,
  },
  logQueryParameters: NODE_ENV === 'development',
  logging: (query, time) => {
    logger.info(time + 'ms' + ' ' + query);
  },
  benchmark: true,
});

export const MYSQL_DB = {
  Account: AccountModel(mysqlConnection),
  ActivityLog: ActivityLogModel(mysqlConnection),
  Customer: CustomerModel(mysqlConnection),
  LoginError: LoginErrorModel(mysqlConnection),
  OTP: OTPModel(mysqlConnection),
  Permission: PermissionModel(mysqlConnection),
  RefreshToken: RefreshTokenModel(mysqlConnection),
  ResetPassword: ResetPasswordModel(mysqlConnection),
  RolePermission: RolePermissionModel(mysqlConnection),
  Role: RoleModel(mysqlConnection),
  User: UserModel(mysqlConnection),
  UserPermission: UserPermissionModel(mysqlConnection),
  UserRole: UserRoleModel(mysqlConnection),
  sequelize: mysqlConnection, // connection instance (RAW queries)
  Sequelize, // library
};
