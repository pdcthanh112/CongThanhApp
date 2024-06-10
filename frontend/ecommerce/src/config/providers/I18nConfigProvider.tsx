import { ReactNode } from "react";
import { getMessages } from "next-intl/server";
import { NextIntlClientProvider } from "next-intl";

export const I18nConfigProvider = async ({ children }: { children: ReactNode }) => {
  const messages = await getMessages();
  return <NextIntlClientProvider messages={messages}>{children}</NextIntlClientProvider>;
};
