import { Notification } from '@models/type/NotificationModel';
import * as actionName from '../name/notification';
import { FetchNotificationFailedPayload, FetchNotificationRequestedPayload, FetchNotificationStartPayload, FetchNotificationSucceededPayload } from '../payload/notification';

export interface NotificationState {
  status: 'idle' | 'pending' | 'succeeded' | 'failed';
  error: string | null;
  data: Notification[];
}

export type FetchNotificationRequested = {
  type: typeof actionName.FETCH_NOTIFICATION_REQUESTED;
  payload: FetchNotificationRequestedPayload;
};

export type FetchNotificationStart = {
  type: typeof actionName.FETCH_NOTIFICATION_START;
  payload: FetchNotificationStartPayload;
};

export type FetchNotificationSucceeded = {
  type: typeof actionName.FETCH_NOTIFICATION_SUCCEEDED;
  payload: FetchNotificationSucceededPayload;
};

export type FetchNotificationFailed = {
  type: typeof actionName.FETCH_NOTIFICATION_FAILED;
  payload: FetchNotificationFailedPayload;
};
