import { HttpException } from './http.exception';

export class ForbiddenException extends HttpException {
  constructor(message: string = 'Forbidden', errorCode: number = 101001) {
    super(500, message, errorCode);
  }
}