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
// import type { NextApiRequest, NextApiResponse } from "next"
// import NextAuth from "next-auth"

import NextAuth, { AuthOptions } from 'next-auth';
import CredentialsProvider from 'next-auth/providers/credentials';
import GoogleProvider from 'next-auth/providers/google';
import FacebookProvider from 'next-auth/providers/facebook';
import TwitterProvider from 'next-auth/providers/twitter';
import AppleProvider from 'next-auth/providers/apple';
// export default async function auth(req: NextApiRequest, res: NextApiResponse) {

//   if(req.query.nextauth.includes("callback") && req.method === "POST") {
//     console.log(
//       "Handling callback request from my Identity Provider",
//       req.body
//     )
//   }

//   // Get a custom cookie value from the request
//   const someCookie = req.cookies["some-custom-cookie"]

//   return await NextAuth(req, res, {
//     callbacks: {
//       session({ session }) {
//         // Return a cookie value as part of the session
//         // This is read when `req.query.nextauth.includes("session") && req.method === "GET"`
//         session.someCookie = someCookie
//         return session
//       }
//     }
//   })
// }

export const authOptions: AuthOptions = {
  providers: [
    GoogleProvider({
      clientId: process.env.GOOGLE_CLIENT_ID!,
      clientSecret: process.env.GOOGLE_CLIENT_SECRET!,
    }),
    FacebookProvider({
      clientId: process.env.FACEBOOK_CLIENT_ID!,
      clientSecret: process.env.FACEBOOK_CLIENT_SECRET!,
    }),
    TwitterProvider({
      clientId: process.env.TWITTER_CLIENT_ID!,
      clientSecret: process.env.TWITTER_CLIENT_SECRET!,
      name: 'Twitter',
    }),
    AppleProvider({
      clientId: process.env.APPLE_CLIENT_ID!,
      clientSecret: process.env.APPLE_CLIENT_SECRET!,
    }),
    CredentialsProvider({
      name: 'Credentials',
      credentials: {
        username: { label: 'Username', type: 'text', placeholder: 'abc@gmail.com' },
        password: { label: 'Password', type: 'password' },
      },
      async authorize(credentials, req) {
        const res = await fetch('http://localhost:8000/auth/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            username: credentials?.username,
            password: credentials?.password,
          }),
        });
        const user = await res.json();

        if (user) {
          return user;
        } else {
          return null;
        }
      },
    }),
  ],

  callbacks: {
    async jwt({ token, user, account }) {
      console.log({ account });

      return { ...token, ...user };
    },
    async session({ session, token, user }) {
      session.user = token as any;

      return session;
    },
  },
  secret: process.env.NEXTAUTH_SECRET,
  pages: {
    signIn: '/auth/login',
  },
};

export default NextAuth(authOptions);
