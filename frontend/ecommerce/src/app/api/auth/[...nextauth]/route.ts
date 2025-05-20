import NextAuth, { NextAuthOptions } from 'next-auth';
import CredentialsProvider from 'next-auth/providers/credentials';
import GoogleProvider from 'next-auth/providers/google';
import FacebookProvider from 'next-auth/providers/facebook';
import TwitterProvider from 'next-auth/providers/twitter';
import AppleProvider from 'next-auth/providers/apple';
import { JWT } from 'next-auth/jwt';

interface ExtendedUser {
  id: string;
  name: string;
  email: string;
  role: string;
  permissions?: string[];
}


interface ExtendedToken extends JWT {
  id?: string;
  role?: string;
  accessToken?: string;
  refreshToken?: string;
  permissions?: string[];
}


interface ExtendedSession {
  user: {
    id: string;
    name: string;
    email: string;
    role: string;
    permissions?: string[];
  };
  accessToken?: string;
  expires: string;
}

export const authOptions: NextAuthOptions = {
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
    }),
    AppleProvider({
      clientId: process.env.APPLE_CLIENT_ID!,
      clientSecret: process.env.APPLE_CLIENT_SECRET!,
    }),
    CredentialsProvider({
      name: 'Credentials',
      credentials: {
        username: {
          label: 'Username',
          type: 'text',
          placeholder: 'abc@gmail.com',
          validate: (value: string) => value?.length > 0 || 'Username is required',
        },
        password: {
          label: 'Password',
          type: 'password',
          placeholder: '**************',
          validate: (value: string) => value?.length > 0 || 'Password is required',
        },
      },
      async authorize(credentials) {
        try {
          const res = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              username: credentials?.username,
              password: credentials?.password,
            }),
          });

          if (!res.ok) {
            console.error('Login API error:', await res.text());
            return null;
          }

          const user = await res.json();
          return user;
        } catch (error) {
          console.log('Error', error);
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

  session: {
    strategy: 'jwt',
    maxAge: 30 * 24 * 60 * 60, // 30 ngày
  },

  callbacks: {
    async signIn({ user, account, profile, email, credentials }) {
      // // Chỉ cho phép đăng nhập với email có domain cụ thể
      // const allowedDomain = 'company.com';
      // if (user.email && !user.email.endsWith(`@${allowedDomain}`)) {
      //   // Cho phép các tài khoản test để demo
      //   if (user.email === 'test@example.com') {
      //     return true;
      //   }
      //   console.log(`User rejected: ${user.email} does not belong to ${allowedDomain}`);
      //   return false; // Từ chối đăng nhập
      // }

      // // Ví dụ: Kiểm tra tài khoản có bị khóa không
      // // const isBlocked = await checkIfUserIsBlocked(user.id);
      // // if (isBlocked) return false;

      // // return email?.verificationRequest; // Cho phép đăng nhập
      return true; // Cho phép đăng nhập
    },
    async jwt({ token, user, account, trigger, session }) {
      if (user) { // Khi đăng nhập lần đầu, user object sẽ có giá trị
        token.id = user.id;
        if ('role' in user) {
          token.role = (user as ExtendedUser).role;
          token.permissions = (user as ExtendedUser).permissions;
        }
           // Lưu accessToken từ OAuth Provider (nếu có)
           if (account) {
            token.accessToken = account.access_token;
            token.refreshToken = account.refresh_token;
            token.provider = account.provider;
          }
      }
      if (trigger === 'update' && session) {
        if (session.user) {
          token.name = session.user.name || token.name;
        }
        token.lastUpdated = Date.now();
      }
     
      if (account?.expires_at && Date.now() > account.expires_at * 1000) {
        return await refreshAccessToken(token);
      }

      return token;
    },
    async session({ session, token, user }) {
      if (token) {
        if (session.user) {
          session.user.id = token.id as string;
          
          // Thêm các trường custom vào session
          if (token.role) {
            session.user.role = token.role as string;
          }
          if (token.permissions) {
            session.user.permissions = token.permissions as string[];
          }
        }
        
        // Thêm access token vào session để client có thể sử dụng
        if (token.accessToken) {
          session.accessToken = token.accessToken as string;
        }
        
      }

      return session;
    },
  },
  debug: process.env.NODE_ENV === 'development',
  secret: process.env.NEXTAUTH_SECRET,
};

const handlers = NextAuth(authOptions);
export default handlers;
// export const { GET, POST } = NextAuth(authOptions);
export { handlers as GET, handlers as POST };

async function refreshAccessToken(token: JWT): Promise<JWT> {
  try {
    const response = await fetch(`/api/auth/refresh`, {
      method: 'POST',
      // headers: {
      //   Authorization: `Bearer ${token.refreshToken}`,
      // },
      body: JSON.stringify({
        refresh_token: token.refreshToken,
      }),
    });
    const tokens = await response.json();

    if (!response.ok) {
      throw tokens;
    }

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
