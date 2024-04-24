// 'use client';
import React from 'react';
import { CustomMeta as Metadata } from '@components/Metadata';

import AppHeader from '@components/AppHeader';
import AppFooter from '@components/AppFooter';
import AppNavbar from '@components/AppNavbar';
import styled from 'styled-components';
import { useAppSelector } from '@redux/store';

import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import AuthModal from '@components/AuthModal';

import { Inter } from 'next/font/google';
const inter = Inter({ subsets: ['latin'] });

const HeaderComponent = styled.header`
  background-image: linear-gradient(to right, rgb(4, 171, 255), rgb(171, 235, 255));
`;

const BodyComponent = styled.main`
  width: 100%;
  min-height: 80vh;
`;

const FooterComponent = styled.footer`
  width: 100%;
`;

type LayoutProps = {
  children: React.ReactNode;
};

// export const metadata = {
//   title: "Online Store",
//   description: "The best online store on the internet.",
//   openGraph: {
//     title: "Online Store",
//     description: "The best online store on the internet.",
//     url: "https://example.com",
//     siteName: "Online Store",
//     type: "website",
//     images: [
//       {
//         url: "https://example.com/og-img.jpg",
//       },
//     ],
//   },
// };

const RootLayout = ({ children }: LayoutProps) => {
  const openModalAuth = useAppSelector((state) => state.modalAuth.isOpenModalAuth);

  return (
    <html lang="en">
      <Metadata />
      <body className={inter.className}>
        <HeaderComponent>
          <AppHeader />
        </HeaderComponent>

        <AppNavbar />

        <BodyComponent>{children}</BodyComponent>

        <FooterComponent>
          <AppFooter />
        </FooterComponent>

        {openModalAuth && <AuthModal />}

        <ToastContainer
          position="top-right"
          autoClose={5000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
          theme="light"
        />
      </body>
    </html>
  );
};

export default RootLayout;

// export const getLayout = (page: React.ReactElement): React.ReactElement => <RootLayout>{page}</RootLayout>;
