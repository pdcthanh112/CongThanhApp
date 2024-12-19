import baseConfig from './jest.config';

export default {
  ...baseConfig,
  testMatch: ['**/test/e2e/**/*.e2e-spec.ts'],
  setupFilesAfterEnv: ['<rootDir>/src/test/e2e/setup.ts'],
}; 
