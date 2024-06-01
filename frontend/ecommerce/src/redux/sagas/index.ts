import { all } from 'redux-saga/effects';

import { authSaga } from './authSaga';
import { categorySaga } from './categorySaga';
import { subcategorySaga } from './subcategorySaga';
import { notificationSaga } from './notificationSaga';
import { wishlistSaga } from './wishlistSaga';
import { cartSaga } from './cartSaga';

export default function* rootSaga() {
  yield all([authSaga(), cartSaga(), categorySaga(), subcategorySaga(), notificationSaga(), wishlistSaga()]);
}
