export enum UserRoleType {
  SUPER_ADMIN = 'super_admin',
  ADMIN = 'admin',
  MANAGER = 'manager',
  CUSTOMER = 'customer',
}

export enum LoginType {
  CREDENTIALS = 'credentials',
  GOOGLE = 'google',
  FACEBOOK = 'facebook',
  TWITTER = 'twitter',
  APPLE = 'apple',
}

export enum ActivityType {
  LOGIN,
  SIGNUP,
  LOGOUT,
  CHANGE_PASSWORD,
  UPDATE_PROFILE,
}
