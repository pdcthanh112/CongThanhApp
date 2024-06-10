export const locales = ["en", "es", "vi"] as const;

export type Locale = (typeof locales)[number];