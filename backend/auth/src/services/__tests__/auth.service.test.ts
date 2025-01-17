// import { AuthService } from '@/services/auth.service';
// import { UserRepository } from '@/repositories/user.repository';
// import { createTestUser } from '@/test/helpers/auth.helper';
// import { UnauthorizedException } from '@/exceptions/unauthorized.exception';

// describe('AuthService', () => {
//   let authService: AuthService;
//   let userRepository: UserRepository;

//   beforeEach(() => {
//     userRepository = new UserRepository();
//     authService = new AuthService(userRepository);
//   });

//   describe('login', () => {
//     it('should return access token when credentials are valid', async () => {
//       const testUser = await createTestUser();
//       const result = await authService.login({
//         email: testUser.email,
//         password: 'password123',
//       });

//       expect(result).toHaveProperty('accessToken');
//       expect(result).toHaveProperty('refreshToken');
//     });

//     it('should throw UnauthorizedException when credentials are invalid', async () => {
//       await expect(
//         authService.login({
//           email: 'wrong@email.com',
//           password: 'wrongpass',
//         })
//       ).rejects.toThrow(UnauthorizedException);
//     });
//   });
// }); 