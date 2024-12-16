import { NextFunction, Request, Response } from 'express';
import { verify } from 'jsonwebtoken';
import { ACCESS_TOKEN_SECRET } from '@config/index';
import { MYSQL_DB } from '@databases/mysql';
import { HttpException } from '@exceptions/httpException';
import { DataStoredInToken, RequestWithUser } from '@interfaces/auth.interface';

const getAuthorization = (req: Request) => {
  const coockie = req.cookies['Authorization'];
  if (coockie) return coockie;

  const token = req.headers['authorization'] as string;
  if (token) return token.split('Bearer ')[1];

  return null;
  // return 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50SWQiOiJlNjE2OWFhMC1kNGE1LTQ3NDAtYjUxMC05Mjg1YmJiNzNhMGUifQ.vb_dRNjiJrNzKqcTVjKYf92nFswkzpuaEtmnOjHevNo';
};

export const AuthMiddleware = async (req: RequestWithUser, res: Response, next: NextFunction) => {
  try {
    const Authorization = getAuthorization(req);
    if (Authorization) {
      const { accountId } = verify(Authorization, ACCESS_TOKEN_SECRET) as DataStoredInToken;
      
      const findUser = (await MYSQL_DB.Customer.findOne({ where: { accountId: accountId } })).dataValues;

      if (findUser) {
        req.user = findUser;
        next();
      } else {
        next(new HttpException(401, 'Unauthorized', 1234));
      }
    } else {
      next(new HttpException(404, 'Authentication token missing', 1234));
    }
  } catch (error) {
    next(new HttpException(401, 'Wrong authentication token', error?.code));
  }
};
