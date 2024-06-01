import { NotificationState } from '@redux/actions/type/notification';
import { PayloadAction, createSlice } from '@reduxjs/toolkit';
import { FetchNotificationFailedPayload, FetchNotificationStartPayload, FetchNotificationSucceededPayload } from '@redux/actions/payload/notification';

const initialState: NotificationState = {
  status: 'idle',
  error: null,
  data: [],
};

const notificationSlice = createSlice({
  name: 'notification',
  initialState: initialState,
  reducers: {
    fetchNotificationStart: (state: NotificationState, action: PayloadAction<FetchNotificationStartPayload>) => {
      state.status = 'pending';
    },
    fetchNotificationSucceeded: (state: NotificationState, action: PayloadAction<FetchNotificationSucceededPayload>) => {
      state.status = 'succeeded';
      state.data = action.payload.data;
    },
    fetchNotificationFailed: (state: NotificationState, action: PayloadAction<FetchNotificationFailedPayload>) => {
      state.status = 'failed';
      state.error = 'loi';
    },
  },
});

export const { fetchNotificationStart, fetchNotificationSucceeded, fetchNotificationFailed } = notificationSlice.actions;
export default notificationSlice.reducer;
