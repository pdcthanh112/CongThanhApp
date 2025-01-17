// import csrf from 'csurf';
// import { Request, Response, NextFunction } from 'express';

// export const csrfProtection = csrf({ 
//   cookie: {
//     httpOnly: true,
//     secure: process.env.NODE_ENV === 'production',
//     sameSite: 'strict'
//   } 
// });

// export const csrfErrorHandler = (err: any, req: Request, res: Response, next: NextFunction) => {
//   if (err.code !== 'EBADCSRFTOKEN') return next(err);
//   res.status(403).json({ 
//     error: 'Invalid CSRF token' 
//   });
// }; 