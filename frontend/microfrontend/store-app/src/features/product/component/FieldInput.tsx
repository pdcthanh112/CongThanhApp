import { Box, TextField, Typography } from "@mui/material";
import React from "react";
import { useFormContext } from "react-hook-form";
import RequiredDot from "../../../components/common/RequiredDot";

type PropsType = {
  name: string;
  title: string;
  placeholder?: string;
  required?: boolean;
};

const FieldInput: React.FC<PropsType> = ({
  name,
  title,
  placeholder,
  required = false,
}) => {
  const {
    register,
    formState: { errors },
  } = useFormContext();

  const error = errors[name]?.message as string | undefined;

  return (
    <Box>
      <Box>
        <Box sx={{display: 'flex'}}><Typography sx={{fontWeight: 500}}>{title}</Typography> {required && <RequiredDot />}</Box>
        <TextField placeholder={placeholder} helperText={error}/>
      </Box>
    </Box>
  );
};

export default FieldInput;
