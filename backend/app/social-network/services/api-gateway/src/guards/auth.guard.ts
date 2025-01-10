import { Injectable, CanActivate, ExecutionContext, Inject} from '@nestjs/common';
import { ClientProxy } from '@nestjs/microservices';
import { firstValueFrom } from 'rxjs';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(
    @Inject('AUTH_SERVICE') private readonly authClient: ClientProxy,
  ) {}

  async canActivate(context: ExecutionContext): Promise<boolean> {
    const request = context.switchToHttp().getRequest();
    const token = request.headers.authorization?.split(' ')[1];

    if (!token) {
      return false;
    }

    try {
      const user = await firstValueFrom(
        this.authClient.send({ cmd: 'verify_token' }, { token }),
      );
      request.user = user;
      return true;
    } catch {
      return false;
    }
  }
}
