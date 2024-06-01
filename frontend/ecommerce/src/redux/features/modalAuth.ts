import { createSlice } from '@reduxjs/toolkit';

const modalAuthSlice = createSlice({
  name: 'modalAuth',
  initialState: {
    isOpenModalAuth: false,
  },
  reducers: {
    openModalAuth: (state) => {
      state.isOpenModalAuth = true;
    },
    closeModalAuth: (state) => {
      state.isOpenModalAuth = false;
    },
    toggleModalAuth: (state) => {
      state.isOpenModalAuth = !state.isOpenModalAuth;
    },
  },
});

export const { openModalAuth, closeModalAuth, toggleModalAuth } = modalAuthSlice.actions;
export default modalAuthSlice.reducer;
