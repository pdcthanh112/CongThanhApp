import { Injectable, NestMiddleware, Inject } from '@nestjs/common';
import { Request, Response, NextFunction } from 'express';
import { ClientProxy } from '@nestjs/microservices';
import { firstValueFrom } from 'rxjs';

@Injectable()
export class AuthMiddleware implements NestMiddleware {
  constructor(
    @Inject('AUTH_SERVICE') private readonly authClient: ClientProxy,
  ) {}

  async use(req: Request, res: Response, next: NextFunction) {
    const token = req.headers.authorization?.split(' ')[1];

    if (token) {
      try {
        const user = await firstValueFrom(
          this.authClient.send({ cmd: 'verify_token' }, { token }),
        );
        req['user'] = user;
      } catch (error) {
        // Token verification failed, but we'll let the service handle it
      }
    }
    next();
  }
}
