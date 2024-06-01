import React from "react";
import { LayoutProps } from "@/app/page";
import AuthModal from "@/components/AuthModal";
import { useAppSelector } from "@/redux/store";

const RootLayout = ({ children }: LayoutProps) => {
  const openModalAuth = useAppSelector((state) => state.modalAuth.isOpenModalAuth);

  return (
    <React.Fragment>
      <div className="min-h-[calc(100vh-150px)]">{children}</div>
      {openModalAuth && <AuthModal />}
    </React.Fragment>
  );
};

export default RootLayout;
