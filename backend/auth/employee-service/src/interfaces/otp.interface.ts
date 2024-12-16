export interface OTP {
  id: number;
  customerId: string;
  code: string;
  expiredAt: Date;
}

export interface CreateOTP {
  customerId: string;
  code: string;
}
