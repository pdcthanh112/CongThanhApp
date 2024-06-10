// import NextAuth from 'next-auth';
// import GoogleProvider from 'next-auth/providers/google';
// import FacebookProvider from 'next-auth/providers/facebook';
// import TwitterProvider from 'next-auth/providers/twitter';
// import AppleProvider from 'next-auth/providers/apple';
// import EmailProvider from 'next-auth/providers/email';
// import CredentialsProvider from 'next-auth/providers/credentials';

// export const authOptions = {
//   providers: [
//     GoogleProvider({
//       clientId: process.env.GOOGLE_CLIENT_ID as string,
//       clientSecret: process.env.GOOGLE_CLIENT_SECRET as string,
//     }),
//     FacebookProvider({
//       clientId: process.env.FACEBOOK_CLIENT_ID as string,
//       clientSecret: process.env.FACEBOOK_CLIENT_SECRET as string,
//     }),
//     TwitterProvider({
//       clientId: process.env.TWITTER_CLIENT_ID as string,
//       clientSecret: process.env.TWITTER_CLIENT_SECRET as string,
//       name: 'Twitter'
//     }),
//     AppleProvider({
//       clientId: process.env.APPLE_CLIENT_ID as string,
//       clientSecret: process.env.APPLE_CLIENT_SECRET as string,
//     }),
//     // EmailProvider({
//     //   server: process.env.MAIL_SERVER || '',
//     //   from: 'pdcthanh112.dev@gmail.com',
//     // }),
//     // CredentialsProvider({
//     //   name: 'Credentials',
//     //   credentials: {
//     //     username: { label: 'Username: ', type: 'text', placeholder: 'pdcthanh112.dev' },
//     //     password: { label: 'Password: ', type: 'password', placeholder: '************' },
//     //   },
//     //   async authorize(credentials, req) {
//     //     const user = { id: '1', name: 'Pham Dao Cong Thanh', email: 'pdcthanh112.dev@gmail.com' };
//     //     if (user) {
//     //       return user;
//     //     } else {
//     //       return null;
//     //     }
//     //   },
//     // }),
//   ],
//   callbacks: {
//     async jwt({ token, account, user }: any) {
//       if (account) token.id = account.userId;
//       return token;
//     },
//     async session({ session, token, user }: any) {
//       session.userId = token.sub;
//       return session;
//     },
//   },
//   secret: process.env.NEXTAUTH_SECRET,
//   pages: {
//     signIn: '/auth/login',
//   },
// };

// const handler = NextAuth(authOptions);
// export { handler as GET, handler as POST };
