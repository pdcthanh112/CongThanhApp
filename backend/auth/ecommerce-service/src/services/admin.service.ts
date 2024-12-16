import { UserModel } from '@/models/user.model';
import { RoleModel } from '@/models/role.model';
import { TokenBlacklistService } from './token-blacklist.service';
import { ForbiddenException, NotFoundException } from '@/exceptions';
import { UserRole, Permission } from '@/interfaces/role.interface';

export class AdminService {
  private tokenBlacklistService = new TokenBlacklistService();
  private userModel = UserModel;
  private roleModel = RoleModel;

  public async suspendUser(adminId: string, userId: string, reason: string): Promise<void> {
    const admin = await this.userModel.findById(adminId);
    if (!this.isAuthorized(admin, 'suspend', 'users')) {
      throw new ForbiddenException('Not authorized to suspend users');
    }

    const user = await this.userModel.findById(userId);
    if (!user) {
      throw new NotFoundException('User not found');
    }

    // Suspend user
    await user.updateOne({ status: 'suspended', suspendReason: reason });
    
    // Revoke all user tokens
    await this.tokenBlacklistService.revokeAllUserTokens(userId);
  }

  public async assignRole(adminId: string, userId: string, role: UserRole): Promise<void> {
    const admin = await this.userModel.findById(adminId);
    if (!this.isAuthorized(admin, 'assign', 'roles')) {
      throw new ForbiddenException('Not authorized to assign roles');
    }

    const user = await this.userModel.findById(userId);
    if (!user) {
      throw new NotFoundException('User not found');
    }

    await user.updateOne({ role });
  }

  public async updatePermissions(adminId: string, roleId: string, permissions: Permission[]): Promise<void> {
    const admin = await this.userModel.findById(adminId);
    if (!this.isSuperAdmin(admin)) {
      throw new ForbiddenException('Only super admins can modify permissions');
    }

    const role = await this.roleModel.findById(roleId);
    if (!role) {
      throw new NotFoundException('Role not found');
    }

    await role.updateOne({ permissions });
  }

  private isAuthorized(user: any, action: string, resource: string): boolean {
    if (!user || !user.role || !user.role.permissions) return false;
    
    return user.role.permissions.some(
      (permission: Permission) =>
        permission.action === action && permission.resource === resource
    );
  }

  private isSuperAdmin(user: any): boolean {
    return user?.role?.name === UserRole.SUPER_ADMIN;
  }
}