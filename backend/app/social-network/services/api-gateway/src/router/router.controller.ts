import { All, Controller, Param, Req, Next } from '@nestjs/common';
import { RouterService } from './router.service';
import { Request } from 'express';

@Controller()
export class RouterController {
  constructor(private readonly routerService: RouterService) {}

  // Auth routes
  @All('auth/*')
  handleAuth(@Req() req: Request) {
    return this.routerService.forward('AUTH_SERVICE', req);
  }

  // User routes
  @All('users/*')
  handleUser(@Req() req: Request) {
    return this.routerService.forward('USER_SERVICE', req);
  }

   // Post routes
   @All('posts/*')
   handlePost(@Req() req: Request) {
     return this.routerService.forward('POST_SERVICE', req);
   }
 
   // Media routes
   @All('media/*')
   handleMedia(@Req() req: Request) {
     return this.routerService.forward('MEDIA_SERVICE', req);
   }
 
   // Notification routes
   @All('notifications/*')
   handleNotification(@Req() req: Request) {
     return this.routerService.forward('NOTIFICATION_SERVICE', req);
   }
 }