import 'reflect-metadata';
import { mysqlConnection } from '@/databases/mysql';
import mongoose from 'mongoose';
// import { redisClient } from '@/databases/redis';

beforeAll(async () => {
  // Setup test database connections
  await mysqlConnection.authenticate();
  await mongoose.connect(process.env.MONGODB_TEST_URL);
//   await redisClient.connect();
});

afterAll(async () => {
  // Cleanup database connections
  await mysqlConnection.close();
  await mongoose.disconnect();
//   await redisClient.quit();
});

afterEach(async () => {
  // Cleanup after each test
  const collections = await mongoose.connection.db.collections();
  for (const collection of collections) {
    await collection.deleteMany({});
  }
  await mysqlConnection.query('SET FOREIGN_KEY_CHECKS = 0');
  const tables = await mysqlConnection.query('SHOW TABLES');
  for (const table of tables[0]) {
    await mysqlConnection.query(`TRUNCATE TABLE ${Object.values(table)[0]}`);
  }
  await mysqlConnection.query('SET FOREIGN_KEY_CHECKS = 1');
//   await redisClient.flushAll();
}); 