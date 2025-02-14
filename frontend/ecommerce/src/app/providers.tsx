'use client';
import React from 'react';
import { LayoutProps } from './[locale]/page';
import { ApolloClient, ApolloProvider, InMemoryCache } from '@apollo/client';
import { SessionProvider } from 'next-auth/react';
import { ReactQueryProvider, ReduxStoreProvider, ThemeProvider } from '@/config/providers';
import { AppContextProvider } from '@/context/AppContext';

const Providers = ({ children }: Readonly<LayoutProps>) => {
  const graphClient = new ApolloClient({
    uri: `${process.env.REACT_APP_API_URL}/graphql`,
    cache: new InMemoryCache(),
  });

  return (
    <AppContextProvider>
      <ThemeProvider attribute="class" defaultTheme="system" enableSystem disableTransitionOnChange>
        <SessionProvider>
          <ApolloProvider client={graphClient}>
            <ReduxStoreProvider>
              <ReactQueryProvider>{children}</ReactQueryProvider>
            </ReduxStoreProvider>
          </ApolloProvider>
        </SessionProvider>
      </ThemeProvider>
    </AppContextProvider>
  );
};

export default Providers;
