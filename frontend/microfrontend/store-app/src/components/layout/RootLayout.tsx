import React from "react";
import AppHeader from "../app-component/AppHeader";
import AppFooter from "../app-component/AppFooter";
import AppSidebar from "../app-component/AppSidebar/AppSidebar";
import AppContent from "../app-component/AppContent";

type PropsType = {
  children: React.ReactElement;
};

const RootLayout = ({ children }: PropsType) => {
  return (
    <div>
      <AppHeader />
      <AppSidebar />
      <AppContent>{children}</AppContent>
      <AppFooter />
    </div>
  );
};

export default RootLayout;
