import createMiddleware from 'next-intl/middleware';
import { NextRequest, NextResponse } from 'next/server';
import { type Locale, locales } from "@/lib/locales";

const nextIntlMiddleware = createMiddleware({
  locales,
  defaultLocale: "en" satisfies Locale,
  // localePrefix: "never",
});

// eslint-disable-next-line import/no-anonymous-default-export
export default function (req: NextRequest): NextResponse {
  return nextIntlMiddleware(req);
}

export const config = {

  matcher: [
    // Match all pathnames except for
    // - … if they start with `/api`, `/_next` or `/_vercel`
    // - … the ones containing a dot (e.g. `favicon.ico`)
    "/((?!api|_next|_vercel|.*\\..*).*)",
  ],
};