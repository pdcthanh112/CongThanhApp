import { Router } from 'express';
import { AdminController } from '@/controllers/admin.controller';
import { AuthMiddleware, ValidationMiddleware, RoleMiddleware } from '@/middlewares/';
import { AssignRoleDto, SuspendUserDto } from '@/dtos/admin.dto';
import { UserRoleType } from '@/constants/enum';

export class AdminRoute {
  public router = Router();
  private adminController = new AdminController();

  constructor() {
    this.initializeRoutes();
  }

  private initializeRoutes() {
    // Protect all admin routes
    this.router.use(AuthMiddleware);
    this.router.use(RoleMiddleware([UserRoleType.ADMIN, UserRoleType.SUPER_ADMIN]));

    // User management
    this.router.post('/users/suspend', ValidationMiddleware(SuspendUserDto), this.adminController.suspendUser);

    // Role management (Super Admin only)
    this.router.post('/users/role', RoleMiddleware([UserRoleType.SUPER_ADMIN]), ValidationMiddleware(AssignRoleDto), this.adminController.assignRole);

    // Token management
    this.router.post('/tokens/revoke', this.adminController.revokeToken);
  }
}
