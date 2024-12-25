import { ReactNode } from 'react';
import { getLocale, getMessages } from 'next-intl/server';
import { NextIntlClientProvider } from 'next-intl';

export const I18nConfigProvider = async ({ children }: { children: ReactNode }) => {
  const messages = await getMessages();
  const locale = await getLocale();
  return (
    <NextIntlClientProvider messages={messages} locale={locale}>
      {children}
    </NextIntlClientProvider>
  );
};
