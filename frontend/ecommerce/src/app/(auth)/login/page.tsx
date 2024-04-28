"use client";
import React from "react";
import LoginForm from "./LoginForm";
import Image from "next/image";
import LoginImage from "@/assets/images/login-page-image.png";

const LoginPage = () => {
  return (
    <div className="grid grid-cols-12 h-full">
      <div className={`col-span-6`}>
        <div className="w-3/5 mx-auto">
          <LoginForm />
        </div>
      </div>
      <div className={`col-span-6 relative`}>
        <Image src={LoginImage} alt={""} objectFit="fit" fill />
      </div>
    </div>
  );
};

export default LoginPage;
