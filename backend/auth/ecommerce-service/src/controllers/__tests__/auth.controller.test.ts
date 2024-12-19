// import request from 'supertest';
// import { app } from '@/app';
// import { createTestUser } from '@/test/helpers/auth.helper';

// describe('AuthController (integration)', () => {
//   describe('POST /auth/login', () => {
//     it('should return 200 and tokens when credentials are valid', async () => {
//       const testUser = await createTestUser();
      
//       const response = await request(app)
//         .post('/auth/login')
//         .send({
//           email: testUser.email,
//           password: 'password123',
//         });

//       expect(response.status).toBe(200);
//       expect(response.body).toHaveProperty('accessToken');
//       expect(response.body).toHaveProperty('refreshToken');
//     });

//     it('should return 401 when credentials are invalid', async () => {
//       const response = await request(app)
//         .post('/auth/login')
//         .send({
//           email: 'wrong@email.com',
//           password: 'wrongpass',
//         });

//       expect(response.status).toBe(401);
//     });
//   });
// }); 