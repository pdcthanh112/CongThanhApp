import { validate } from 'class-validator';
import { plainToClass } from 'class-transformer';
import { Request, Response, NextFunction } from 'express';

export const validateInput = (dtoClass: any) => {
  return async (req: Request, res: Response, next: NextFunction) => {
    const dtoObj = plainToClass(dtoClass, req.body);
    const errors = await validate(dtoObj, { 
      whitelist: true, 
      forbidNonWhitelisted: true 
    });

    if (errors.length > 0) {
      const errorMessages = errors.map(error => ({
        property: error.property,
        constraints: error.constraints,
      }));
      
      return res.status(400).json({ 
        error: 'Validation failed', 
        details: errorMessages 
      });
    }

    req.body = dtoObj;
    next();
  };
}; 