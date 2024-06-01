import { Notification } from '@models/type';

export interface FetchNotificationRequestedPayload {
  accountId: string;
}
export interface FetchNotificationStartPayload {
  params: { token: string };
}
export interface FetchNotificationSucceededPayload {
  data: Notification[];
}
export interface FetchNotificationFailedPayload {
  error: string;
}
