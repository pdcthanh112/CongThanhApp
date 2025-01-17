import { createClient, RedisClientType } from 'redis';
import { logger } from '@/utils/logger';

export class RedisService {
  private static instance: RedisService;
  private client: RedisClientType;

  // constructor() {
  //   if (RedisService.instance) {
  //     return RedisService.instance;
  //   }
  //   this.initialize();
  //   RedisService.instance = this;
  // }

  // private async initialize() {
  //   try {
  //     this.client = createClient({
  //       url: process.env.REDIS_URL,
  //       socket: {
  //         reconnectStrategy: retries => {
  //           if (retries > 10) {
  //             return new Error('Redis connection retries exceeded');
  //           }
  //           return Math.min(retries * 100, 3000);
  //         },
  //       },
  //     });

  //     this.client.on('error', err => logger.error('Redis Client Error', err));
  //     this.client.on('connect', () => logger.info('Redis Client Connected'));

  //     await this.client.connect();
  //   } catch (error) {
  //     logger.error('Redis initialization failed:', error);
  //     throw error;
  //   }
  // }

  // public async set(key: string, value: string, expiryMode?: string, time?: number): Promise<void> {
  //   try {
  //     if (expiryMode && time) {
  //       await this.client.set(key, value, { EX: time });
  //     } else {
  //       await this.client.set(key, value);
  //     }
  //   } catch (error) {
  //     logger.error('Redis set operation failed:', error);
  //     throw error;
  //   }
  // }

  // public async get(key: string): Promise<string | null> {
  //   try {
  //     return await this.client.get(key);
  //   } catch (error) {
  //     logger.error('Redis get operation failed:', error);
  //     throw error;
  //   }
  // }

  // public async del(key: string): Promise<void> {
  //   try {
  //     await this.client.del(key);
  //   } catch (error) {
  //     logger.error('Redis delete operation failed:', error);
  //     throw error;
  //   }
  // }

  // public async exists(key: string): Promise<boolean> {
  //   try {
  //     const result = await this.client.exists(key);
  //     return result === 1;
  //   } catch (error) {
  //     logger.error('Redis exists operation failed:', error);
  //     throw error;
  //   }
  // }

  // public async flushAll(): Promise<void> {
  //   try {
  //     await this.client.flushAll();
  //   } catch (error) {
  //     logger.error('Redis flush operation failed:', error);
  //     throw error;
  //   }
  // }

  // public async disconnect(): Promise<void> {
  //   try {
  //     await this.client.quit();
  //   } catch (error) {
  //     logger.error('Redis disconnect failed:', error);
  //     throw error;
  //   }
  // }
}
