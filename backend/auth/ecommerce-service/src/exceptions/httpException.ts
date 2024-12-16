export class HttpException extends Error {
  public status: number;
  public message: string;
  public errorCode: number;

  constructor(status: number, message: string, errorCode: number) {
    super(message);
    this.status = status;
    this.message = message;
    this.errorCode = errorCode;
  }
}
