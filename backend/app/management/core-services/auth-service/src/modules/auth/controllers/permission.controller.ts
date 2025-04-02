import { Body, Controller, Get, Post } from '@nestjs/common';
import { PermissionService } from '../services/permission.service';
import { CreatePermissionDto } from '../dtos/create-permission.dto';

@Controller('permissions')
export class PermissionController {
  constructor(private readonly permissionService: PermissionService) {}

  @Get()
  async findAll() {
    return this.permissionService.findAll();
  }

  @Post()
  async createPermission(@Body() data: CreatePermissionDto) {
    return this.permissionService.createPremission(data);
  }
}
