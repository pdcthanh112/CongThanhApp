import jwt from 'jsonwebtoken';
// import { User } from '@/core/entities/user.entity';

export const generateTestToken = (user: any) => {
  return jwt.sign(
    { id: user.id, email: user.email },
    process.env.ACCESS_TOKEN_SECRET,
    { expiresIn: '15m' }
  );
};

export const createTestUser = async () => {
  // Create test user logic
}; 