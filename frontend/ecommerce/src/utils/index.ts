import { defaultLocale } from "@/lib/locales";

export const getBaseUrl = () => {
  if (process.env.NEXT_PUBLIC_APP_URL) {
    return process.env.NEXT_PUBLIC_APP_URL;
  }

  if (
    process.env.VERCEL_ENV === 'production'
    && process.env.VERCEL_PROJECT_PRODUCTION_URL
  ) {
    return `https://${process.env.VERCEL_PROJECT_PRODUCTION_URL}`;
  }

  if (process.env.VERCEL_URL) {
    return `https://${process.env.VERCEL_URL}`;
  }

  return 'http://localhost:3000';
};

export const getI18nPath = (url: string, locale: string) => {
  if (locale === defaultLocale) {
    return url;
  }

  return `/${locale}${url}`;
};

export const slugGenerator = (name: string) => {
  const slug =
    name &&
    name
      .toString()
      .toLowerCase()
      .normalize("NFKD")
      .replace(/[\u0300-\u036f]/g, "")
      .replace(/\s*\/\s*/g, "-")
      .replace(/\s*&amp;\s*/g, "-and-")
      .replace(/\s+/g, "-")
      .replace(/apos;/g, "")
      .replace(/_/g, "")
      .replace(/[^\w-]+/g, "");
  return slug === "" ? "unknown" : slug;
};
