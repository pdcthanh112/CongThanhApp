import { ExtractJwt, Strategy } from 'passport-jwt';
import { PassportStrategy } from '@nestjs/passport';
import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { JwtPayload } from '../interfaces/jwt-payload.interface';

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
  constructor(private configService: ConfigService) {
    super({
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
      ignoreExpiration: false,
      secretOrKey: configService.get('JWT_SECRET'), // Cùng secret key với auth-service
    });
  }

  async validate(payload: JwtPayload) {
    // JwtStrategy sẽ tự động verify token signature
    // Nếu token hợp lệ, trả về thông tin user để gắn vào request
    return {
      id: payload.sub,
      email: payload.email
    };
  }
}
