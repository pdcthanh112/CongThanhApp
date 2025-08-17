import { Box } from "@mui/material";
import React from "react";
import { useFormContext } from "react-hook-form";
import RequiredDot from "../../../components/common/RequiredDot";

type PropsType = {
  name: string;
  title: string
  placeholder?: string;
  required?: boolean
};

const FieldInput: React.FC<PropsType> = ({ name, title, placeholder, required = false }) => {
  const {
    register,
    formState: { errors },
  } = useFormContext();

  const error = errors[name]?.message as string | undefined;

  return <Box>
    <Box>{title} {required && <RequiredDot />}</Box>
  </Box>;
};

export default FieldInput;
