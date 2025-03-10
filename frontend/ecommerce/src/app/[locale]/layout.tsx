import '../globals.css';
import { Inter } from 'next/font/google';
import AppHeader from '@/components/AppHeader';
import AppFooter from '@/components/AppFooter';
import AppNavbar from '@/components/AppNavbar';
import Providers from '../providers';
import { RootLayout } from '@/layout';
import { type Locale } from '@/lib/locales';
import { getTranslations } from 'next-intl/server';
import { Metadata } from 'next';
import { routing } from '@/i18n/routing';
import { notFound } from 'next/navigation';
import { getMessages } from 'next-intl/server';
import { NextIntlClientProvider } from 'next-intl';

const inter = Inter({ subsets: ['latin'] });

export const baseOpenGraph = {
  locale: 'en_US',
  type: 'website',
  siteName: 'CongThanhApp - Ecommerce',
};

export async function generateMetadata({ params }: { params: Promise<{ locale: Locale }> }): Promise<Metadata> {
  const { locale } = await params;
  const t = await getTranslations({ locale });

  return {
    title: t('metadata.title'),
    description: t('metadata.description'),
    openGraph: {
      ...baseOpenGraph,
      title: t('metadata.title'),
      description: t('metadata.description'),
      url: 'localhost:3000',
      images: [
        {
          url: 'https://th.bing.com/th/id/R.ca0f4aeff51ff7e15c440816546f7730?rik=eJ5AEYKPVEnIIA&pid=ImgRaw&r=0&sres=1&sresct=1',
        },
      ],
    },
    alternates: {
      canonical: 'localhost:3000',
    },
  };
}

type LayoutPropsType = {
  children: React.ReactNode;
  params: {
    locale: Locale;
  };
};

export default async function Layout({ children, params: { locale } }: Readonly<LayoutPropsType>) {
  if (!routing.locales.includes(locale as any)) {
    notFound();
  }

  const messages = await getMessages();

  return (
    <html lang={locale} suppressHydrationWarning>
      <body className={inter.className}>
        <Providers>
          <NextIntlClientProvider messages={messages}>
            <AppHeader />
            <AppNavbar />
            <RootLayout>
              <main className="min-h-[calc(100vh-270px)] bg-[#F5F5F5]">{children}</main>
            </RootLayout>
            <AppFooter />
          </NextIntlClientProvider>
        </Providers>
      </body>
    </html>
  );
}
