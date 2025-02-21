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
      //   name: "Google"
    }),
    FacebookProvider({
      clientId: process.env.FACEBOOK_CLIENT_ID!,
      clientSecret: process.env.FACEBOOK_CLIENT_SECRET!,
      name: 'Facebook',
    }),
    TwitterProvider({
      clientId: process.env.TWITTER_CLIENT_ID!,
      clientSecret: process.env.TWITTER_CLIENT_SECRET!,
      name: 'Twitter',
    }),
    AppleProvider({
      clientId: process.env.APPLE_CLIENT_ID!,
      clientSecret: process.env.APPLE_CLIENT_SECRET!,
      name: 'Apple',
    }),
    CredentialsProvider({
      name: 'Credentials',
      credentials: {
        username: { label: 'Username', type: 'text', placeholder: 'abc@gmail.com' },
        password: { label: 'Password', type: 'password', placeholder: '**************' },
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

  pages: {
    signIn: '/auth/login',
    signOut: '/auth/sign-out',
    error: '/auth/error',
    newUser: undefined, // If set, new users will be directed here on first sign in
    verifyRequest: '/auth/verify-request', // (used for check email message)
  },

  callbacks: {
    async jwt({ token, user, account }) {
      if (Date.now() > account?.expires_at!!) {
        return refreshAccessToken(token);
      }
      console.log({ account });
      return { ...token, ...user };
    },
    async session({ session, token, user }) {
      // session.user = token.sub as any;
      session.user = user;
      session.expires = token.sub!!;
      return session;
    },
  },
  secret: process.env.NEXTAUTH_SECRET,
};

export default NextAuth(authOptions);

const handler = NextAuth(authOptions);
export { handler as GET, handler as POST };

async function refreshAccessToken(token: any) {
  try {
    const response = await fetch(`${process.env.API_SERVER_BASE_URL}/api/auth/refresh`, {
      headers: {
        Authorization: `Bearer ${token.refreshToken}`,
      },
    });
    const tokens = await response.json();

    if (!response.ok) {
      throw tokens;
    }

    /*const refreshedTokens = {
      "access_token": "acess-token",
      "expires_in": 2,
      "refresh_token": "refresh-token"
    }*/

    //return token;

    return {
      ...token,
      accessToken: tokens.accessToken,
      refreshToken: tokens.refreshToken ?? token.refreshToken, // Fall back to old refresh token
    };
  } catch (error) {
    console.log(error);

    return {
      ...token,
      error: 'RefreshAccessTokenError',
    };
  }
}
