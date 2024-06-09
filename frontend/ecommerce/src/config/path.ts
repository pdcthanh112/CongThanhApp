// const path = {
//   account: '/account',
//   address: '/manage/address',
//   bankingAccount: 'banking-account',
//   cart: '/cart',
//   changePassword: '/auth/change-password',
//   history: '/history',
//   home: '/',
//   login: '/auth/login',
//   logout: '/logout',
//   notification: '/manage/notification',
//   order: '/order',
//   product: '/product/:slug',
//   profile: '/manage/profile',
//   resetPassword: '/auth/reset-password',
//   signup: '/auth/signup',
//   vouchers: '/manage/vouchers',
//   wishlist: '/wishlist',
// } as const;
// export default path;

const PRODUCT_BASE_URL = 'product'

export const COMMON_URL = {
  HOME: '/'
}

export const AUTH_PATH_URL = {
  LOGIN: '/login',
  REGISTER: '/register',
  FORGET_PASSWORD: '/forget-password',
  RESET_PASSWORD: '/reset-password',
  LOG_OUT: '/logout'
}

export const PRODUCT_PATH_URL = {
  PRODUCT: PRODUCT_BASE_URL,
  PRODUCT_LIST: `/${PRODUCT_BASE_URL}/`,
  PRODUCT_DETAIL: `/${PRODUCT_BASE_URL}/:product-detail`,
};
