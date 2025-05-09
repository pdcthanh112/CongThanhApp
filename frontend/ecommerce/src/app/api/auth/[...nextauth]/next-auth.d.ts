import { DefaultSession } from 'next-auth';

declare module 'next-auth' {
  interface Session {
    user: {
      accountId: string;
      role: string[];
    };
    token: { accessToken: string } & DefaultSession['user'];
  }
}
