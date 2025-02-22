export enum StateStatus {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE',
}

export enum ResponseStatus {
  SUCCESS = 'SUCCESS',
  FAILURE = 'FAILURE',
}

export enum StatusName {
  APPROVED = 'APPROVED',
  PENDING = 'PENDING',
  REJECTED = 'REJECTED',
  CANCELED = 'CANCELED',
}

export enum GENDER {
  MALE,
  FEMALE,
  OTHER,
}

export type CardType = 'visa' | 'mastercard' | 'amex' | 'discover' | 'jcb' | 'unionpay' | 'unknown';
