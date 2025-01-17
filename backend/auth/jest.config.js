import { pathsToModuleNameMapper } from 'ts-jest';
import { compilerOptions } from './tsconfig.json';

export const preset = 'ts-jest';
export const testEnvironment = 'node';
export const roots = ['<rootDir>/src'];
export const transform = {
  '^.+\\.tsx?$': 'ts-jest',
};
export const moduleNameMapper = pathsToModuleNameMapper(compilerOptions.paths, { prefix: '<rootDir>/src' });
export const setupFilesAfterEnv = ['<rootDir>/src/test/setup.ts'];
export const testMatch = [
  '**/__tests__/**/*.test.ts',
  '**/__tests__/**/*.spec.ts'
];
export const collectCoverageFrom = [
  'src/**/*.{js,ts}',
  '!src/**/*.d.ts',
  '!src/test/**/*',
  '!src/**/index.ts',
];
export const coverageThreshold = {
  global: {
    branches: 80,
    functions: 80,
    lines: 80,
    statements: 80,
  },
};
export const coverageDirectory = 'coverage';
export const testPathIgnorePatterns = ['/node_modules/', '/dist/'];
