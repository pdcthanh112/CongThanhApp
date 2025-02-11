import { put, takeEvery } from 'redux-saga/effects';
import * as actionName from '../actions/name/wishlist';
import { addProductToWishlist, getWishlistByCustomer, removeProductFromWishlist } from '@/api/wishlistApi';
import { PayloadAction } from '@reduxjs/toolkit';
import {
  addItemToWishlistFailed,
  addItemToWishlistStart,
  addItemToWishlistSucceeded,
  fetchWishlistFailed,
  fetchWishlistStart,
  fetchWishlistSucceeded,
  removeItemFromWishlistFailed,
  removeItemFromWishlistStart,
  removeItemFromWishlistSucceeded,
} from '@/redux/reducers/wishlistReducer';

function* fetchWishlistByCustomer(action: PayloadAction<any>) {
  try {
    yield put(fetchWishlistStart(action.payload));
    const { data } = yield getWishlistByCustomer(action.payload.customerId);
    yield put(fetchWishlistSucceeded({ data: data }));
  } catch (e) {
    yield put(fetchWishlistFailed(action.payload));
  }
}

function* addItemToWishlist(action: PayloadAction<any>) {
  try {
    yield put(addItemToWishlistStart(action.payload));
    yield addProductToWishlist(action.payload.customerId, action.payload.productId);
    yield put(addItemToWishlistSucceeded(action.payload));
  } catch (e: any) {
    yield put(addItemToWishlistFailed(e.response.data));
  }
}

function* removeItemFromWishlist(action: PayloadAction<any>) {
  try {
    yield put(removeItemFromWishlistStart(action.payload));
    yield removeProductFromWishlist(action.payload.customerId, action.payload.productId);
    yield put(removeItemFromWishlistSucceeded(action.payload));
  } catch (e: any) {
    yield put(removeItemFromWishlistFailed(e.response.data));
  }
}

export function* wishlistSaga() {
  yield takeEvery(actionName.FETCH_WISHLIST_REQUESTED, fetchWishlistByCustomer);
  yield takeEvery(actionName.ADD_ITEM_TO_WISHLIST_REQUESTED, addItemToWishlist);
  yield takeEvery(actionName.REMOVE_ITEM_FROM_WISHLIST_REQUESTED, removeItemFromWishlist);
}
