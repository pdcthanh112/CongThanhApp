// import request from 'supertest';
// import { app } from '@/app';
// import { createTestUser } from '@/test/helpers/auth.helper';

// describe('Authentication Flow (E2E)', () => {
//   it('should complete full authentication flow', async () => {
//     // 1. Register
//     const registerResponse = await request(app)
//       .post('/auth/register')
//       .send({
//         email: 'test@example.com',
//         password: 'password123',
//         name: 'Test User',
//       });

//     expect(registerResponse.status).toBe(201);

//     // 2. Login
//     const loginResponse = await request(app)
//       .post('/auth/login')
//       .send({
//         email: 'test@example.com',
//         password: 'password123',
//       });

//     expect(loginResponse.status).toBe(200);
//     const { accessToken } = loginResponse.body;

//     // 3. Access protected route
//     const protectedResponse = await request(app)
//       .get('/api/profile')
//       .set('Authorization', `Bearer ${accessToken}`);

//     expect(protectedResponse.status).toBe(200);

//     // 4. Logout
//     const logoutResponse = await request(app)
//       .post('/auth/logout')
//       .set('Authorization', `Bearer ${accessToken}`);

//     expect(logoutResponse.status).toBe(200);
//   });
// }); 