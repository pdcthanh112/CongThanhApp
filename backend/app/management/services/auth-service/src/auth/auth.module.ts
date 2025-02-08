import { Module } from '@nestjs/common';
import { AuthController } from './auth.controller';
import { AuthService } from './auth.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Account, Role, Permission, AuditLog, RolePermission, UserRole, UserPermission } from './entities';

@Module({
  controllers: [AuthController],
  providers: [AuthService],
  imports: [TypeOrmModule.forFeature([Account, AuditLog, Role, Permission, RolePermission, UserRole, UserPermission])],
})
export class AuthModule {}
