import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Permission } from '../entities';
import { Repository } from 'typeorm';
import { CreatePermissionDto } from '../dtos/create-permission.dto';

@Injectable()
export class PermissionService {
  constructor(@InjectRepository(Permission) private readonly permissionRepository: Repository<Permission>) {}

  async findAll(): Promise<Permission[]> {
    return await this.permissionRepository.find();
  }

  async createPremission(createPermissionDto: CreatePermissionDto): Promise<Permission> {
    const permission = this.permissionRepository.create(createPermissionDto);
    return await this.permissionRepository.save(permission);
  }
}
