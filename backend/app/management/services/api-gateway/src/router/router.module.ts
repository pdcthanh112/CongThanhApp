import { Module } from '@nestjs/common';
import { ClientsModule, Transport } from '@nestjs/microservices';
import { ConfigService } from '@nestjs/config';
import { RouterController } from './router.controller';
import { RouterService } from './router.service';

@Module({
  imports: [
    ClientsModule.registerAsync([
      {
        name: 'AUTH_SERVICE',
        useFactory: (configService: ConfigService) => ({
          transport: Transport.TCP,
          options: {
            host: configService.get('services.auth.host'),
            port: configService.get('services.auth.port'),
          },
        }),
        inject: [ConfigService],
      },
      {
        name: 'USER_SERVICE',
        useFactory: (configService: ConfigService) => ({
          transport: Transport.TCP,
          options: {
            host: configService.get('services.user.host'),
            port: configService.get('services.user.port'),
          },
        }),
        inject: [ConfigService],
      },
      // Tương tự cho các services khác...
    ]),
  ],
  controllers: [RouterController],
  providers: [RouterService],
})

export class RouterModule {}
