"use client";
import React from "react";
import { LayoutProps } from "./[locale]/page";
import { ThemeProvider } from "@/components/Theme/ThemeProviders";
import { ApolloClient, ApolloProvider, InMemoryCache } from "@apollo/client";
import { SessionProvider } from "next-auth/react";
import { Provider } from "react-redux";
import { ReactQueryProvider, ReduxStoreProvider } from "@/config/providers";
import { I18nConfigProvider } from "@/config/providers/I18nConfigProvider";

const Providers = ({ children }: Readonly<LayoutProps>) => {
  const graphClient = new ApolloClient({
    uri: `${process.env.REACT_APP_API_URL}/graphql`,
    cache: new InMemoryCache(),
  });

  return (
    <ThemeProvider attribute="class" defaultTheme="system" enableSystem disableTransitionOnChange>
      <SessionProvider session={null}>
        <ApolloProvider client={graphClient}>
          <ReduxStoreProvider>
            <ReactQueryProvider>{children}</ReactQueryProvider>
          </ReduxStoreProvider>
        </ApolloProvider>
      </SessionProvider>
    </ThemeProvider>
  );
};

export default Providers;
