const PRODUCT_BASE_URL = "product";
const MANAGE_BASE_URL = "manage";
const CART_BASE_URL = "cart";
const ORDER_BASE_URL = "order";
const WISHLIST_BASE_URL = "wishlist";
const HISTORY_BASE_URL = "history";

export const PATH = {
  HOME: "/",
  AUTH_PATH_URL: {
    LOGIN: "/login",
    REGISTER: "/register",
    FORGET_PASSWORD: "/forget-password",
    RESET_PASSWORD: "/reset-password",
    LOG_OUT: "/logout",
  },
  MANAGE_PATH_URL: {
    ACCOUNT: `/${MANAGE_BASE_URL}/account`,
    PROFILE: `/${MANAGE_BASE_URL}/profile`,
  },
  PRODUCT_PATH_URL: {
    PRODUCT: PRODUCT_BASE_URL,
    PRODUCT_LIST: `/${PRODUCT_BASE_URL}/`,
    PRODUCT_DETAIL: `/${PRODUCT_BASE_URL}/:product-detail`,
  },
  CART: `/${CART_BASE_URL}/`,
  ORDER: `/${ORDER_BASE_URL}/`,
  WISHLIST: `/${WISHLIST_BASE_URL}/`,
  HISTORY: `/${HISTORY_BASE_URL}/`,
};
