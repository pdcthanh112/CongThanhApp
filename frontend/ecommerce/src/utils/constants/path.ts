const PRODUCT_BASE_URL = 'product';
const MANAGE_BASE_URL = 'manage';
const CART_BASE_URL = 'cart';
const ORDER_BASE_URL = 'order';
const WISHLIST_BASE_URL = 'wishlist';
const HISTORY_BASE_URL = 'history';
const NOTIFICATION_BASE_URL = 'notification';

export const PATH = {
  HOME: '/',
  AUTH_PATH_URL: {
    LOGIN: '/login',
    REGISTER: '/register',
    FORGET_PASSWORD: '/forget-password',
    RESET_PASSWORD: '/reset-password',
    LOG_OUT: '/logout',
  },
  MANAGE_PATH_URL: {
    ACCOUNT: `/${MANAGE_BASE_URL}/account`,
    PROFILE: `/${MANAGE_BASE_URL}/profile`,
  },
  PRODUCT_PATH_URL: {
    PRODUCT: PRODUCT_BASE_URL,
    PRODUCT_LIST: `/${PRODUCT_BASE_URL}/`,
    PRODUCT_DETAIL: `/${PRODUCT_BASE_URL}/`,
  },
  CART_PATH_URL: {
    CART: `/${CART_BASE_URL}/`,
  },
  ORDER_PATH_URL: {
    ORDER: `/${ORDER_BASE_URL}/`,
  },
  WISHLIST_PATH_URL: {
    WISHLIST: `/${WISHLIST_BASE_URL}/`,
  },
  HISTORY_PATH_URL: {
    HISTORY: `/${HISTORY_BASE_URL}/`,
  },
  NOTIFICATION_PATH_URL: {
    NOTIFICATION: `/${NOTIFICATION_BASE_URL}/`,
  },
};

import AppLogo from '@/assets/images/app-logo.png';
import DefaultImage from '@/assets/images/default-image.jpg';

export const IMAGE = {
  appLogo: AppLogo,
  defaultImage: DefaultImage,
  appLogoBlack: '/assets/icons/ic_logo_black.svg',
  appLogoSmall: '/assets/images/app_logo_small.png',
  loginPageImage: '/assets/images/login-page-image.png',
};
