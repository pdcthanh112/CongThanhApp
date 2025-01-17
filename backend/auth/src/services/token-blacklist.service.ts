import { RedisService } from "./redis.service";

export class TokenBlacklistService {
    private redis: RedisService;
    private readonly PREFIX = 'blacklist:token:';
  
    constructor() {
      this.redis = new RedisService();
    }
  
    public async revokeToken(token: string, reason?: string): Promise<void> {
      const tokenHash = this.hashToken(token);
      const expiresIn = process.env.ACCESS_TOKEN_EXPIRED;
      
      await this.redis.set(
        `${this.PREFIX}${tokenHash}`,
        reason || 'Revoked',
        'EX',
        Number(expiresIn)
      );
    }
  
    public async isTokenRevoked(token: string): Promise<boolean> {
      const tokenHash = this.hashToken(token);
      const result = await this.redis.get(`${this.PREFIX}${tokenHash}`);
      return !!result;
    }
  
    public async revokeAllUserTokens(userId: string): Promise<void> {
      // Implement logic to revoke all tokens for a specific user
      await this.redis.set(
        `${this.PREFIX}user:${userId}`,
        'All tokens revoked',
        'EX',
        Number(process.env.ACCESS_TOKEN_EXPIRED)
      );
    }
  
    private hashToken(token: string): string {
      // Implement a secure hashing mechanism
      return require('crypto')
        .createHash('sha256')
        .update(token)
        .digest('hex');
    }
  }