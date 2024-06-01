import * as actionName from './name/subcategory';
import { FetchSubcategoryFailedPayload, FetchSubcategoryStartPayload, FetchSubcategorySucceededPayload } from './payload/subcategory';
import { FetchSubcategoryFailed, FetchSubcategoryStart, FetchSubcategorySucceeded } from './type/subcategory';

export const fetchSubcategoryRequested = () => ({
  type: actionName.FETCH_SUBCATEGORY_REQUESTED,
  payload: null
})

export const fetchSubcategoryStart = (payload: FetchSubcategoryStartPayload): FetchSubcategoryStart => ({
  type: actionName.FETCH_SUBCATEGORY_START,
  payload,
});

export const fetchSubcategorySucceeded = (payload: FetchSubcategorySucceededPayload): FetchSubcategorySucceeded => ({
  type: actionName.FETCH_SUBCATEGORY_SUCCEEDED,
  payload,
});

export const fetchSubcategoryFailed = (payload: FetchSubcategoryFailedPayload): FetchSubcategoryFailed => ({
  type: actionName.FETCH_SUBCATEGORY_FAILED,
  payload,
});
