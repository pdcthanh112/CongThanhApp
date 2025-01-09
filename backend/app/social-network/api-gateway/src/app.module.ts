import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ConfigModule } from '@nestjs/config';
import { GuardsModule } from './guards/guards.module';
import { RouterModule } from './router/router.module';
import configuration from './config/configuration';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
      load: [configuration],
    }),
    GuardsModule,
    RouterModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
