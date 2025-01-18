import Sequelize from 'sequelize';
import { NODE_ENV, MYSQL_HOST, MYSQL_PORT, MYSQL_USER, MYSQL_PASSWORD, MYSQL_DATABASE } from '@/config/index';
import { logger } from '@/utils/logger';
import CustomerModel from '@/models/customer.model';
import { AccountModel, ActivityLogModel, LoginErrorModel, OTPModel, RefreshTokenModel, UserModel, ResetPasswordModel } from '@/models/';
import {  } from '@/models/resetPassword.model';

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
  Customer: CustomerModel(mysqlConnection),
  ActivityLog: ActivityLogModel(mysqlConnection),
  OTP: OTPModel(mysqlConnection),
  LoginError: LoginErrorModel(mysqlConnection),
  RefreshToken: RefreshTokenModel(mysqlConnection),
  User: UserModel(mysqlConnection),
  ResetPassword: ResetPasswordModel(mysqlConnection),
  mysqlConnection, // connection instance (RAW queries)
  Sequelize, // library
};
