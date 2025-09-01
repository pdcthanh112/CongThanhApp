import React from "react";
import { Box, Typography } from "@mui/material";
import FieldInput from "../../../features/product/component/FieldInput";

import TextEditor from "../../../features/product/component/TextEditor";

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

<TextEditor /> 
    </Box>
  );
};

export default InformationSession;
