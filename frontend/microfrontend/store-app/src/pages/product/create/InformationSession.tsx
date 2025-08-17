import React from "react";
import { Box, Typography } from "@mui/material";
import FieldInput from "../../../features/product/component/FieldInput";

const InformationSession = () => {
  return (
    <Box>
      <Typography>InformationSession</Typography>
      <FieldInput
        name="productName"
        title="Product name"
        placeholder="Please input product name"
        required
      />
    </Box>
  );
};

export default InformationSession;
