import { Request, Response, NextFunction } from 'express';
import { ForbiddenException } from '@/exceptions/forbidden.exception';
import { UserRole } from '@/interfaces/role.interface';

export const RoleMiddleware = (allowedRoles: UserRole[]) => {
  return (req: Request, res: Response, next: NextFunction) => {
    try {
      const userRole = req.user?.role;

      if (!userRole || !allowedRoles.includes(userRole as UserRole)) {
        throw new ForbiddenException('Access denied');
      }

      next();
    } catch (error) {
      next(error);
    }
  };
};
