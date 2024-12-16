import { Request, Response, NextFunction } from 'express';
import { AdminService } from '@/services/admin.service';
import { TokenBlacklistService } from '@/services/token-blacklist.service';

export class AdminController {
  private adminService = new AdminService();
  private tokenBlacklistService = new TokenBlacklistService();

  public suspendUser = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const { userId, reason } = req.body;
      const adminId = req.user.sub;

      await this.adminService.suspendUser(adminId, userId, reason);

      res.status(200).json({
        success: true,
        message: 'User suspended successfully'
      });
    } catch (error) {
      next(error);
    }
  };

  public assignRole = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const { userId, role } = req.body;
      const adminId = req.user.sub;

      await this.adminService.assignRole(adminId, userId, role);

      res.status(200).json({
        success: true,
        message: 'Role assigned successfully'
      });
    } catch (error) {
      next(error);
    }
  };

  public revokeToken = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const { token, reason } = req.body;
      await this.tokenBlacklistService.revokeToken(token, reason);

      res.status(200).json({
        success: true,
        message: 'Token revoked successfully'
      });
    } catch (error) {
      next(error);
    }
  };
}