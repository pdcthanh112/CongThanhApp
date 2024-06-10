'use client'
import React from "react";
import { LayoutProps } from "@/app/[locale]/page";
import AuthModal from "@/components/AuthModal";
import { useAppSelector } from "@/redux/store";
import { ToastContainer } from "react-toastify";

export const RootLayout = ({ children }: LayoutProps) => {
  const openModalAuth = useAppSelector((state) => state.modalAuth.isOpenModalAuth);

  return (
    <React.Fragment>
      {children}
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
    </React.Fragment>
  );
};
