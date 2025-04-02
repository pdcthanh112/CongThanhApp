// src/common/middlewares/user-context.middleware.ts
import { Injectable, NestMiddleware } from '@nestjs/common';
import { Request, Response, NextFunction } from 'express';
import { REQUEST_USER } from '../constants';

@Injectable()
export class UserContextMiddleware implements NestMiddleware {
  use(req: Request, res: Response, next: NextFunction) {
    // const user = req?.user;
    const user = null;
    if (user) {
      global[REQUEST_USER] = user;
    }
    next();
  }
}
