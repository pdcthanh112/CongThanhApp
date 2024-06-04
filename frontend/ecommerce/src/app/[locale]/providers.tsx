import React from "react";
import { LayoutProps } from "./page";
import { ThemeProvider } from "@/components/Theme/ThemeProviders";
import { ApolloClient, ApolloProvider, InMemoryCache } from "@apollo/client";
import { SessionProvider } from "next-auth/react";
import { Provider } from "react-redux";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { persistor, store } from "@/redux/store";
import { PersistGate } from "redux-persist/integration/react";

const Providers = ({ children }: Readonly<LayoutProps>) => {
  const graphClient = new ApolloClient({
    uri: `${process.env.REACT_APP_API_URL}/graphql`,
    cache: new InMemoryCache(),
  });

  const queryClient = new QueryClient();

  return (
    <ThemeProvider attribute="class" defaultTheme="system" enableSystem disableTransitionOnChange>
      <SessionProvider session={null}>
        <ApolloProvider client={graphClient}>
          <Provider store={store}>
            <PersistGate loading={<div>Loading...</div>} persistor={persistor}>
              <QueryClientProvider client={queryClient}>
                {children}
              </QueryClientProvider>
            </PersistGate>
          </Provider>
        </ApolloProvider>
      </SessionProvider>
    </ThemeProvider>
  );
};

export default Providers;
