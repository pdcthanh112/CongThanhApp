import { PayloadAction } from '@reduxjs/toolkit';
import { put, takeEvery } from 'redux-saga/effects';
import * as api from 'api/subcategoryApi';
import { fetchSubcategoryFailed, fetchSubcategoryStart, fetchSubcategorySucceeded } from '@/redux/reducers/subcategoryReducer';
import { FETCH_SUBCATEGORY_REQUESTED } from '@/redux/actions/name/subcategory';

function* fetchSubcategory(action: PayloadAction<any>) {
  try {
    yield put(fetchSubcategoryStart(action.payload));
    const { data } = yield api.getAllSubategory();
    yield put(fetchSubcategorySucceeded({data: data}));
  } catch (e) {
    yield put(fetchSubcategoryFailed(action.payload));
  }
}

export function* subcategorySaga() {
  yield takeEvery(FETCH_SUBCATEGORY_REQUESTED, fetchSubcategory);
}
