import { put, takeEvery } from 'redux-saga/effects';
import * as actionName from '../actions/name/notification';
import { getNotificationByCustomer } from 'api/notificationApi';
import { PayloadAction } from '@reduxjs/toolkit';
import { fetchNotificationFailed, fetchNotificationStart, fetchNotificationSucceeded } from '@/redux/reducers/notificationReducer';

function* fetchNotification(action: PayloadAction<any>) {
  try {
    yield put(fetchNotificationStart(action.payload));
    const {data} =  yield getNotificationByCustomer(action.payload);
    yield put(fetchNotificationSucceeded({data: data}));
  } catch (e) {
    yield put(fetchNotificationFailed(action.payload));
  }
}

export function* notificationSaga() {
  yield takeEvery(actionName.FETCH_NOTIFICATION_REQUESTED, fetchNotification);
}
