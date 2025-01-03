import { WishlistState } from '@/redux/actions/type/wishlist';
import { PayloadAction, createSlice } from '@reduxjs/toolkit';
import {
  AddItemToWishlistFailedPayload,
  AddItemToWishlistStartPayload,
  AddItemToWishlistSucceededPayload,
  FetchWishlistFailedPayload,
  FetchWishlistStartPayload,
  FetchWishlistSucceededPayload,
  RemoveItemFromWishlistFailedPayload,
  RemoveItemFromWishlistStartPayload,
  RemoveItemFromWishlistSucceededPayload,
} from '@/redux/actions/payload/wishlist';

const initialState: WishlistState = {
  status: 'idle',
  error: null,
  data: null,
};

const wishlistSlice = createSlice({
  name: 'wishlist',
  initialState: initialState,
  reducers: {
    fetchWishlistStart: (state: WishlistState, action: PayloadAction<FetchWishlistStartPayload>) => {
      state.status = 'pending';
    },
    fetchWishlistSucceeded: (state: WishlistState, action: PayloadAction<FetchWishlistSucceededPayload>) => {
      state.status = 'succeeded';
      state.data = action.payload.data;
    },
    fetchWishlistFailed: (state: WishlistState, action: PayloadAction<FetchWishlistFailedPayload>) => {
      state.status = 'failed';
      state.error = { errorCode: 523534, message: 'loi' };
    },
    addItemToWishlistStart: (state: WishlistState, action: PayloadAction<AddItemToWishlistStartPayload>) => {
      state.status = 'pending';
    },
    addItemToWishlistSucceeded: (state: WishlistState, action: PayloadAction<AddItemToWishlistSucceededPayload>) => {
      state.status = 'succeeded';
      state.data = action.payload.data;
    },
    addItemToWishlistFailed: (state: WishlistState, action: PayloadAction<AddItemToWishlistFailedPayload>) => {
      state.status = 'failed';
      state.error = action.payload;
    },
    addItemToWishlistClean: (state: WishlistState) => {
      state.status = 'idle';
      state.error = null;
    },
    removeItemFromWishlistStart: (state: WishlistState, action: PayloadAction<RemoveItemFromWishlistStartPayload>) => {
      state.status = 'pending';
    },
    removeItemFromWishlistSucceeded: (state: WishlistState, action: PayloadAction<RemoveItemFromWishlistSucceededPayload>) => {
      state.status = 'succeeded';
      state.data = action.payload.data;
    },
    removeItemFromWishlistFailed: (state: WishlistState, action: PayloadAction<RemoveItemFromWishlistFailedPayload>) => {
      state.status = 'failed';
      state.error = action.payload.error;
    },
    removeItemFromWishlistClean: (state: WishlistState) => {
      state.status = 'idle';
      state.error = null;
    },
  },
});

export const {
  fetchWishlistStart,
  fetchWishlistSucceeded,
  fetchWishlistFailed,
  addItemToWishlistStart,
  addItemToWishlistSucceeded,
  addItemToWishlistFailed,
  addItemToWishlistClean,
  removeItemFromWishlistStart,
  removeItemFromWishlistSucceeded,
  removeItemFromWishlistFailed,
  removeItemFromWishlistClean,
} = wishlistSlice.actions;
export default wishlistSlice.reducer;
