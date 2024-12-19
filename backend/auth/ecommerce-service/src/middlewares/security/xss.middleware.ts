import { Request, Response, NextFunction } from 'express';
// import sanitizeHtml from 'sanitize-html';

export const xssMiddleware = (req: Request, res: Response, next: NextFunction) => {
//   if (req.body) {
//     for (const [key, value] of Object.entries(req.body)) {
//       if (typeof value === 'string') {
//         req.body[key] = sanitizeHtml(value, {
//           allowedTags: [], // No HTML tags allowed
//           allowedAttributes: {}, // No HTML attributes allowed
//           disallowedTagsMode: 'recursiveEscape',
//         });
//       }
//     }
//   }
  next();
}; 