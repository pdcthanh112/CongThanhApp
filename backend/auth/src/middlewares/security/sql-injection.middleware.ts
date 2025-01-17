import { Request, Response, NextFunction } from 'express';

export const sqlInjectionMiddleware = async (req: Request, res: Response, next: NextFunction) => {
  const sqlInjectionPattern = /(\b(SELECT|INSERT|UPDATE|DELETE|DROP|UNION|ALTER|CREATE|TRUNCATE)\b)|(['"])/gi;
  
  const checkForSQLInjection = (value: string): boolean => {
    return sqlInjectionPattern.test(value);
  };

  const params = { ...req.body, ...req.query, ...req.params };
  
  for (const value of Object.values(params)) {
    if (typeof value === 'string' && checkForSQLInjection(value)) {
      return res.status(400).json({ 
        error: 'Potential SQL injection detected' 
      });
    }
  }

  next();
}; 