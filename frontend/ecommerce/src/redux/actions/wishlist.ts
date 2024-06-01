import {
  AddItemToWishlistFailedPayload,
  AddItemToWishlistRequestedPayload,
  AddItemToWishlistStartPayload,
  AddItemToWishlistSucceededPayload,
  FetchWishlistFailedPayload,
  FetchWishlistRequestedPayload,
  FetchWishlistStartPayload,
  FetchWishlistSucceededPayload,
  RemoveItemFromWishlistFailedPayload,
  RemoveItemFromWishlistRequestedPayload,
  RemoveItemFromWishlistStartPayload,
  RemoveItemFromWishlistSucceededPayload,
} from './payload/wishlist';
import {
  AddItemToWishlistClean,
  AddItemToWishlistFailed,
  AddItemToWishlistRequested,
  AddItemToWishlistStart,
  AddItemToWishlistSucceeded,
  FetchWishlistFailed,
  FetchWishlistRequested,
  FetchWishlistStart,
  FetchWishlistSucceeded,
  RemoveItemFromWishlistClean,
  RemoveItemFromWishlistFailed,
  RemoveItemFromWishlistRequested,
  RemoveItemFromWishlistStart,
  RemoveItemFromWishlistSucceeded,
} from './type/wishlist';
import * as actionName from './name/wishlist';

export const fetchWishlistRequested = (payload: FetchWishlistRequestedPayload): FetchWishlistRequested => ({
  type: actionName.FETCH_WISHLIST_REQUESTED,
  payload,
});

export const fetchWishlistStart = (payload: FetchWishlistStartPayload): FetchWishlistStart => ({
  type: actionName.FETCH_WISHLIST_START,
  payload,
});

export const fetchWishlistSucceeded = (payload: FetchWishlistSucceededPayload): FetchWishlistSucceeded => ({
  type: actionName.FETCH_WISHLIST_SUCCEEDED,
  payload,
});

export const fetchWishlistFailed = (payload: FetchWishlistFailedPayload): FetchWishlistFailed => ({
  type: actionName.FETCH_WISHLIST_FAILED,
  payload,
});

export const addItemToWishlistRequested = (payload: AddItemToWishlistRequestedPayload): AddItemToWishlistRequested => ({
  type: actionName.ADD_ITEM_TO_WISHLIST_REQUESTED,
  payload,
});

export const addItemToWishlistStart = (payload: AddItemToWishlistStartPayload): AddItemToWishlistStart => ({
  type: actionName.ADD_ITEM_TO_WISHLIST_START,
  payload,
});

export const addItemToWishlistSuccess = (payload: AddItemToWishlistSucceededPayload): AddItemToWishlistSucceeded => ({
  type: actionName.ADD_ITEM_TO_WISHLIST_SUCCEEDED,
  payload,
});

export const addItemToWishlistFailed = (payload: AddItemToWishlistFailedPayload): AddItemToWishlistFailed => ({
  type: actionName.ADD_ITEM_TO_WISHLIST_FAILED,
  payload,
});

export const addItemToWishlistClean = (): AddItemToWishlistClean => ({
  type: actionName.ADD_ITEM_TO_WISHLIST_CLEAN,
  payload: null,
});

export const removeItemFromWishlistRequested = (payload: RemoveItemFromWishlistRequestedPayload): RemoveItemFromWishlistRequested => ({
  type: actionName.REMOVE_ITEM_FROM_WISHLIST_REQUESTED,
  payload,
});

export const removeItemFromWishlistStart = (payload: RemoveItemFromWishlistStartPayload): RemoveItemFromWishlistStart => ({
  type: actionName.REMOVE_ITEM_FROM_WISHLIST_START,
  payload,
});

export const removeItemFromWishlistSucceeded = (payload: RemoveItemFromWishlistSucceededPayload): RemoveItemFromWishlistSucceeded => ({
  type: actionName.REMOVE_ITEM_FROM_WISHLIST_SUCCEEDED,
  payload,
});

export const removeItemFromWishlistFailed = (payload: RemoveItemFromWishlistFailedPayload): RemoveItemFromWishlistFailed => ({
  type: actionName.REMOVE_ITEM_FROM_WISHLIST_FAILED,
  payload,
});

export const removeItemFromWishlistClean = (): RemoveItemFromWishlistClean => ({
  type: actionName.REMOVE_ITEM_FROM_WISHLIST_CLEAN,
  payload: null,
});
