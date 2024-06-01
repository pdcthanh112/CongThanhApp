// import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
// import * as authApi from '@apis/customerApi';
// import { Customer, LoginForm } from '@models/CustomerModel';

// export const login = createAsyncThunk('auth/login', async (data: LoginForm, thunkAPI) => {
//   try {
//     const response = await authApi.login(data.email, data.password);
//     return response.data;
//   } catch (error) {
//     return thunkAPI.rejectWithValue(error);
//   }
// });

// export const signup = createAsyncThunk('auth/register', async ({ data }: any, thunkAPI) => {
//   try {
//     const response = await authApi.signup(data);
//     return response.data;
//   } catch (error) {
//     return thunkAPI.rejectWithValue(error);
//   }
// });

// export const logout = createAsyncThunk('auth/logout', async (_, thunkAPI) => {
//   try {
//     // const response = await authApi.signup(data);
//     // return response.data;
//     return null;
//   } catch (error) {
//     return thunkAPI.rejectWithValue(error);
//   }
// });

// interface IAuthState {
//   login: {
//     loading: boolean;
//     currentUser: any;
//     error: boolean;
//     success: boolean;
//   };
//   signup: {
//     loading: boolean;
//     userInfo: {};
//     error: '';
//     success: boolean;
//   };
//   logout: {
//     loading: boolean;
//     error: boolean;
//   };
//   edit: {
//     loading: boolean;
//     error: boolean;
//   };
// }

// const authSlice = createSlice({
//   name: 'auth',
//   initialState: {
//     login: {
//       loading: false,
//       currentUser: null,
//       error: false,
//       success: false,
//     },
//     signup: {
//       loading: false,
//       userInfo: {},
//       error: '',
//       success: false,
//     },
//     logout: {
//       loading: false,
//       error: false,
//     },
//     edit: {
//       loading: false,
//       error: false,
//     },
//   } as IAuthState,
//   reducers: {},
//   extraReducers: (builder) => {
//     builder.addCase(login.pending, (state, action) => {
//       // return { ...state, state.login.loading = true}
//       state.login.loading = true;
//     });
//     builder.addCase(login.fulfilled, (state, action) => {
//       state.login.loading = false;
//       state.login.success = true;
//       state.login.error = false;
//       state.login.currentUser = action.payload.data;
//     });
//     builder.addCase(login.rejected, (state, action) => {
//       state.login.error = true;
//     });
//     builder.addCase(logout.pending, (state, action) => {
//       // return { ...state, state.login.loading = true}
//       state.login.loading = true;
//     });
//     builder.addCase(logout.fulfilled, (state, action) => {
//       state.login.loading = false;
//       state.login.success = true;
//       state.login.error = false;
//       state.login.currentUser = null;
//       // state.login.currentUser.userInfo = {};
//       // state.login.currentUser.tokenData = {};
//     });
//     builder.addCase(logout.rejected, (state, action) => {
//       state.login.error = true;
//     });
//   },
// });

// // export const { loginStart, loginSuccess, loginFailed, logoutStart, logoutSuccess, logoutFailed, editStart, editSuccess, editFailed } = authSlice.actions;
// export default authSlice.reducer;
