import Sequelize from 'sequelize';
import { NODE_ENV, MYSQL_HOST, MYSQL_PORT, MYSQL_USER, MYSQL_PASSWORD, MYSQL_DATABASE } from '@config/index';
import { logger } from '@utils/logger';
import EmployeeModel from '@models/employee.model';
import CandidateModel from '@models/candidate.model';
import CustomerModel from '@models/customer.model';
import LoginErrorModel from '@models/loginError.model';
import OTPModel from '@models/otp.model';
import RefreshTokenModel from '@models/refreshToken.model';

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
  },
  logQueryParameters: NODE_ENV === 'development',
  logging: (query, time) => {
    logger.info(time + 'ms' + ' ' + query);
  },
  benchmark: true,
});

export const MYSQL_DB = {
  Employee: EmployeeModel(mysqlConnection),
  Customer: CustomerModel(mysqlConnection),
  Candidate: CandidateModel(mysqlConnection),
  OTP: OTPModel(mysqlConnection),
  LoginError: LoginErrorModel(mysqlConnection),
  RefreshToken: RefreshTokenModel(mysqlConnection),
  mysqlConnection, // connection instance (RAW queries)
  Sequelize, // library
};
