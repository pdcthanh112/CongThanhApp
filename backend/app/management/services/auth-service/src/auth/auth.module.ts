import { Module } from '@nestjs/common';
import { AuthController } from './auth.controller';
import { AuthService } from './auth.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Account, Role, Permission, AuditLog, RolePermission, UserRole, UserPermission } from './entities';
import { PermissionController, RoleController } from './controllers/';
import { PermissionService, RoleService } from './services';

@Module({
  controllers: [AuthController, RoleController, PermissionController],
  providers: [AuthService, RoleService, PermissionService],
  imports: [TypeOrmModule.forFeature([Account, AuditLog, Role, Permission, RolePermission, UserRole, UserPermission])],
  exports: [RoleService, PermissionService],
})

export class AuthModule {}
