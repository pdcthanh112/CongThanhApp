import { put, takeEvery } from 'redux-saga/effects';
import * as actionName from '../actions/name/cart';
import { PayloadAction } from '@reduxjs/toolkit';
import {
  addItemToCartFailed,
  addItemToCartStart,
  addItemToCartSucceeded,
  createNewCartFailed,
  createNewCartStart,
  createNewCartSucceeded,
  deleteCartFailed,
  deleteCartStart,
  deleteCartSucceeded,
  fetchCartClear,
  fetchCartFailed,
  fetchCartStart,
  fetchCartSucceeded,
  removeItemFromCartFailed,
  removeItemFromCartStart,
  removeItemFromCartSucceeded,
  updateItemQuantityStart,
  updateItemQuantitySucceeded,
} from '@/redux/reducers/cartReducer';
import { getCartByCustomerId } from '@/api/cartApi';

function* fetchCart(action: PayloadAction<any>) {
  try {
    yield put(fetchCartStart(action.payload));
    const { data } = yield getCartByCustomerId(action.payload);
    yield put(fetchCartSucceeded({ data: data }));
  } catch (error) {
    yield put(fetchCartFailed({ error: 'Error:' + error }));
  } finally {
    yield put(fetchCartClear(action.payload));
  }
}

function* createNewCart(action: PayloadAction<any>) {
  try {
    yield put(createNewCartStart(action.payload));
    // yield addToCartApi({ productId: action.payload.productId, quantity: action.payload.quantity, cartId: action.payload.cartId });
    yield put(createNewCartSucceeded(action.payload));
  } catch (error) {
    yield put(createNewCartFailed({ error: 'Error: ' + error }));
  } finally {
    yield put();
  }
}

function* deleteCart(action: PayloadAction<any>) {
  try {
    yield put(deleteCartStart(action.payload));
    // yield removeCartItem(action.payload);
    yield put(deleteCartSucceeded(action.payload));
  } catch (error) {
    yield put(deleteCartFailed({ error: 'Error: ' + error }));
  }
}

function* addToCart(action: PayloadAction<any>) {
  try {
    yield put(addItemToCartStart(action.payload));

    yield put(addItemToCartSucceeded(action.payload));
  } catch (error) {
    yield put(addItemToCartFailed({ error: 'Error' + error }));
  }
}

function* removeFromCart(action: PayloadAction<any>) {
  try {
    yield put(removeItemFromCartStart(action.payload));
    // yield Api.removeCartItem(action.payload);
    yield put(removeItemFromCartSucceeded(action.payload));
  } catch (error) {
    yield put(removeItemFromCartFailed({ error: 'Error' + error }));
  }
}

function* updateItemQuantity(action: PayloadAction<any>) {
  try {
    yield put(updateItemQuantityStart(action.payload));
    //gọi api
    yield put(updateItemQuantitySucceeded(action.payload))
  } catch (error) {
    
  }
}

export function* cartSaga() {
  yield takeEvery(actionName.FETCH_CART_REQUESTED, fetchCart);
  yield takeEvery(actionName.CREATE_NEW_CART_REQUESTED, createNewCart);
  yield takeEvery(actionName.DELETE_CART_REQUESTED, deleteCart);
  yield takeEvery(actionName.ADD_ITEM_TO_CART_REQUESTED, addToCart);
  yield takeEvery(actionName.REMOVE_ITEM_FROM_CART_REQUESTED, removeFromCart);
  yield takeEvery(actionName.UPDATE_ITEM_QUANTITY_REQUEST, updateItemQuantity);
}
