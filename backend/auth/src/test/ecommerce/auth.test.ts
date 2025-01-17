import request from 'supertest';
import app from '@/app'; 

describe('Login API', () => {
  it('should return a valid JWT token upon successful login', async () => {
    const response = await request(app)
      .post('/login')
      .send({ username: 'example_user', password: 'password123' });

    expect(response.status).toBe(200);
    expect(response.body).toHaveProperty('token');
    // Add more assertions as needed
  });

  it('should return an error message for invalid credentials', async () => {
    const response = await request(app)
      .post('/login')
      .send({ username: 'example_user', password: 'incorrect_password' });

    expect(response.status).toBe(401);
    expect(response.body).toHaveProperty('error');
    // Add more assertions as needed
  });
});
