import { HttpException } from './http.exception';

export class NotFoundException extends HttpException {
  constructor(message: string = 'Not Found', errorCode: number = 101001) {
    super(404, message, errorCode);
  }
}
