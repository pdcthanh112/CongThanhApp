import { combineReducers } from "redux";

import authReducer from "./authReducer";
import cartReducer from "./cartReducer";
import categoryReducer from "./categoryReducer";

import modalAuthReducer from "@/redux/features/modalAuth";

export const rootReducer = combineReducers({
    auth: authReducer,
    cart: cartReducer,
    category: categoryReducer,
    modalAuth: modalAuthReducer
  });