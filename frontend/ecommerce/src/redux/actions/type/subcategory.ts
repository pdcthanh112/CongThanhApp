import { Subcategory } from '@models/type/SubcategoryModel';
import * as actionName from '../name/subcategory';
import { FetchSubcategoryFailedPayload, FetchSubcategoryStartPayload, FetchSubcategorySucceededPayload } from '../payload/subcategory';

export interface SubcategoryState {
  status: 'idle' | 'pending' | 'succeeded' | 'failed'
  error: string | null;
  data: Subcategory[];
}

export type FetchSubcategoryStart = {
  type: typeof actionName.FETCH_SUBCATEGORY_START;
  payload: FetchSubcategoryStartPayload;
};

export type FetchSubcategorySucceeded = {
  type: typeof actionName.FETCH_SUBCATEGORY_SUCCEEDED;
  payload: FetchSubcategorySucceededPayload;
};

export type FetchSubcategoryFailed = {
  type: typeof actionName.FETCH_SUBCATEGORY_FAILED;
  payload: FetchSubcategoryFailedPayload;
};
