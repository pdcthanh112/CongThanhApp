import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { BlogService } from './blog/blog.service';
import { BlogController } from './blog/blog.controller';
import { BlogModule } from './blog/blog.module';

@Module({
  imports: [BlogModule],
  controllers: [AppController, BlogController],
  providers: [AppService, BlogService],
})
export class AppModule {}
