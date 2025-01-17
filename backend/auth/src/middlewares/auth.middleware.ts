import { NextFunction, Request, Response } from 'express';
import { verify } from 'jsonwebtoken';
import { ACCESS_TOKEN_SECRET } from '@/config/index';
import { TokenPayload } from '@/interfaces/auth.interface';
import { UnauthorizedException } from '@/exceptions/unauthorized.exception';

export const AuthMiddleware = async (req: Request, res: Response, next: NextFunction) => {
  try {
    const Authorization = req.headers['Authorization'] as string;
    if (!Authorization) {
      next(new UnauthorizedException('Authentication token missing', 1234));
    }
    const token = Authorization.split(' ')[1];
    const decoded = verify(token, ACCESS_TOKEN_SECRET) as TokenPayload;

    req.user = decoded;
    next();
  } catch (error) {
    next(new UnauthorizedException('Wrong authentication token', error.code));
  }
};
