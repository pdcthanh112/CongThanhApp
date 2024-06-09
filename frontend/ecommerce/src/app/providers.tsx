import React from "react";
import { LayoutProps } from "./[locale]/page";
import { ThemeProvider } from "@/components/Theme/ThemeProviders";
import { ApolloClient, ApolloProvider, InMemoryCache } from "@apollo/client";
import { SessionProvider } from "next-auth/react";
import { Provider } from "react-redux";
import { persistor, store } from "@/redux/store";
import { PersistGate } from "redux-persist/integration/react";
import { ReactQueryProvider } from "@/config/providers";

const Providers = ({ children }: Readonly<LayoutProps>) => {
  const graphClient = new ApolloClient({
    uri: `${process.env.REACT_APP_API_URL}/graphql`,
    cache: new InMemoryCache(),
  });

  return (
    <ThemeProvider attribute="class" defaultTheme="system" enableSystem disableTransitionOnChange>
      <SessionProvider session={null}>
        <ApolloProvider client={graphClient}>
          <Provider store={store}>
            <PersistGate loading={<div>Loading...</div>} persistor={persistor}>
              <ReactQueryProvider>{children}</ReactQueryProvider>
            </PersistGate>
          </Provider>
        </ApolloProvider>
      </SessionProvider>
    </ThemeProvider>
  );
};

export default Providers;
