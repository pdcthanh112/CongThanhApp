import NextAuth, { AuthOptions } from 'next-auth';
import CredentialsProvider from 'next-auth/providers/credentials';
import GoogleProvider from 'next-auth/providers/google';
import FacebookProvider from 'next-auth/providers/facebook';
import TwitterProvider from 'next-auth/providers/twitter';
import AppleProvider from 'next-auth/providers/apple';

export const authOptions: AuthOptions = {
  providers: [
    GoogleProvider({
      clientId: process.env.GOOGLE_CLIENT_ID!,
      clientSecret: process.env.GOOGLE_CLIENT_SECRET!,
      authorization: {
        params: {
          prompt: 'consent',
          access_type: 'offline',
          response_type: 'code',
        },
      },
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
        email: { label: 'email', type: 'text', placeholder: 'abc@gmail.com' },
        password: { label: 'Password', type: 'password', placeholder: '**************' },
      },
      async authorize(credentials, req) {
        if (!credentials) return null;
        const res = await fetch('http://localhost:8000/auth/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            email: credentials.email,
            password: credentials.password,
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

  session: {
    strategy: 'jwt',
  },

  pages: {
    signIn: '/auth/login',
    signOut: '/auth/sign-out',
    error: '/auth/error',
    newUser: undefined, // If set, new users will be directed here on first sign in
    verifyRequest: '/auth/verify-request', // (used for check email message)
  },

  callbacks: {
    async jwt({ token, user, account }) {
      // if (Date.now() > account?.expires_at!!) {
      //   return refreshAccessToken(token);
      // }
      if (account) {
        token.accessToken = account.access_token;
        token.refreshToken = account.refresh_token;
        token.accessTokenExpires = account.expires_at;
        if (user) {
          token.user = user;
        }
      }
      return token;
    },
    async session({ session, token, user }) {
      session.user = token.user; // Truyền thông tin người dùng
      session.accessToken = token.accessToken; // Thêm accessToken vào session
      session.refreshToken = token.refreshToken; // Thêm refreshToken (nếu cần)
      session.expires = token.accessTokenExpires; // Truyền thời gian hết hạn
      return session;
    },
  },
  secret: process.env.NEXTAUTH_SECRET,
};

export default NextAuth(authOptions);
const handler = NextAuth(authOptions);
export { handler as GET, handler as POST };

// export const { GET, POST } = handler;

// async function refreshAccessToken(token: any) {
//   console.log('Refreshing access token', token);
//   try {
//     console.log('Beaarer token', `Bearer ${token.refreshToken}`);

//     // const response = await fetch(`${process.env.API_SERVER_BASE_URL}/api/auth/refresh`, {
//     const response = await fetch(`http://localhost:8000/ecommerce/auth/refresh-token`, {
//       method: 'POST',
//       headers: {
//         Authorization: `Bearer ${token.refreshToken}`,
//       },
//     });

//     console.log(response);

//     const tokens = await response.json();

//     console.log(tokens);

//     if (!response.ok) {
//       throw tokens;
//     }

//     /*const refreshedTokens = {
//       "access_token": "acess-token",
//       "expires_in": 2,
//       "refresh_token": "refresh-token"
//     }*/

//     //return token;

//     return {
//       ...token,
//       accessToken: tokens.accessToken,
//       refreshToken: tokens.refreshToken ?? token.refreshToken, // Fall back to old refresh token
//     };
//   } catch (error) {
//     console.log(error);

//     return {
//       ...token,
//       error: 'RefreshAccessTokenError',
//     };
//   }
// }
