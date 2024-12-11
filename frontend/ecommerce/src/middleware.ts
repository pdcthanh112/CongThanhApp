import createMiddleware from 'next-intl/middleware';
import { NextRequest, NextResponse } from 'next/server';
import { type Locale, locales } from '@/lib/locales';
import { getToken } from 'next-auth/jwt';
import { PUBLIC_ROUTES, LOGIN, ROOT, PROTECTED_SUB_ROUTES } from '@/lib/routes';

export default async function middleware(request: NextRequest): Promise<NextResponse> {
  const response = nextIntlMiddleware(request);

  const token = await getToken({ req: request });
  const isAuthenticated = !!token;
  const { pathname } = request.nextUrl;

  const isPublicRoute =
    PUBLIC_ROUTES.some((route) => pathname.startsWith(route)) ||
    pathname === ROOT ||
    !PROTECTED_SUB_ROUTES.some((route) => pathname.includes(route));

  if (!isAuthenticated && !isPublicRoute) {
    return NextResponse.redirect(new URL(LOGIN, request.url));
  }

  return response;
}

const nextIntlMiddleware = createMiddleware({
  locales,
  defaultLocale: 'en' satisfies Locale,
  localePrefix: "never",
});

export const config = {
  matcher: [
    '/((?!api|_next|_vercel|.*\\..*).*)',
  ],
};
