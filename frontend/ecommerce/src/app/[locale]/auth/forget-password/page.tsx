"use client";
import React, { useState } from "react";
import { Card } from "@mui/material";
import ForgetPasswordForm from "./ForgetPasswordForm";
import InputOtpForm from "./InputOtpForm";

export default function ForgetPasswordPage () {
  const [isConfirmSuceed, setIsconfirmSuceed] = useState<boolean>(true);

  return (
    <React.Fragment>
      <Card className="w-2/5 mx-auto px-3 py-2">
        <ForgetPasswordForm />
      </Card>

      <InputOtpForm open={isConfirmSuceed} setOpen={setIsconfirmSuceed} />
    </React.Fragment>
  );
};
