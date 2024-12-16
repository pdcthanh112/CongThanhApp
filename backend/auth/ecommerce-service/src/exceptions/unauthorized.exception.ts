import { HttpException } from './http.exception';

export class UnauthorizedException extends HttpException {
  constructor(message: string = 'Unauthorized', errorCode: number = 101001) {
    super(401, message, errorCode);
  }
}