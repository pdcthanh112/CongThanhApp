import { combineReducers } from "redux";

import authReducer from "./authReducer";
import cartReducer from "./cartReducer";
import categoryReducer from "./categoryReducer";
import subcategoryReducer from "./subcategoryReducer";
import notificationReducer from "./notificationReducer";
import wishlistReducer from "./wishlistReducer";

import modalAuthReducer from "@/redux/features/modalAuth";

export const rootReducer = combineReducers({
    auth: authReducer,
    cart: cartReducer,
    category: categoryReducer,
    subcategory: subcategoryReducer,
    notification: notificationReducer,
    wishlist: wishlistReducer,
    modalAuth: modalAuthReducer
  });