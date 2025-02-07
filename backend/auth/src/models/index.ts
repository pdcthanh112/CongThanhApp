import { AccountModel } from './account.model';
import { ActivityLogModel } from './activityLog.model';
import { CustomerModel } from './customer.model';
import { LoginErrorModel } from './loginError.model';
import { OTPModel } from './otp.model';
import { PermissionModel } from './permission.model';
import { RefreshTokenModel } from './refreshToken.model';
import { ResetPasswordModel } from './resetPassword.model';
import { RolePermissionModel } from './role-permission.model';
import { RoleModel } from './role.model';
import { UserRoleModel } from './user-role.model';
import { UserModel } from './user.model';
import { UserPermissionModel } from './user-permission.model';

// UserModel.belongsToMany(RoleModel, { through: UserRoleModel });
// RoleModel.belongsToMany(UserModel, { through: UserRoleModel });

// RoleModel.belongsToMany(PermissionModel, { through: RolePermissionModel });
// PermissionModel.belongsToMany(RoleModel, { through: RolePermissionModel });

// export {
//   AccountModel,
//   ActivityLogModel,
//   CustomerModel,
//   LoginErrorModel,
//   OTPModel,
//   PermissionModel,
//   RefreshTokenModel,
//   ResetPasswordModel,
//   RolePermissionModel,
//   RoleModel,
//   UserRoleModel,
//   UserModel,
//   UserPermissionModel,
// };
export { default as AccountModel } from './account.model';
export { default as ActivityLogModel } from './activityLog.model';
export { default as CustomerModel } from './customer.model';
export { default as LoginErrorModel } from './loginError.model';
export { default as OTPModel } from './otp.model';
export { default as PermissionModel } from './permission.model';
export { default as RefreshTokenModel } from './refreshToken.model';
export { default as ResetPasswordModel } from './resetPassword.model';
export { default as RolePermissionModel } from './role-permission.model';
export { default as RoleModel } from './role.model';
export { default as UserRoleModel } from './user-role.model';
export { default as UserModel } from './user.model';
export { default as UserPermissionModel } from './user-permission.model';
