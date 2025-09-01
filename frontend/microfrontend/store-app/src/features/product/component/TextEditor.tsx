import React from "react";
import { Box, styled } from "@mui/material";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
// import { Essentials, Paragraph, Bold, Italic } from "ckeditor5";
// import { FormatPainter } from "ckeditor5-premium-features";

import "ckeditor5/ckeditor5.css";
import "ckeditor5-premium-features/ckeditor5-premium-features.css";

const TextEditor = () => {
  const BoxEditor = styled(Box)(({ theme }) => ({
    "ck ck-content ck-editor__editable ck-rounded-corners ck-editor__editable_inline ck-blurred":
      {
        height: "300px",
      },
  }));

  return (
    <BoxEditor>
      <CKEditor
        editor={ClassicEditor as any}
        data="<p>Hello from CKEditor 5 in React!</p>"
        onChange={(event, editor) => {
          const data = editor.getData();
          console.log("Editor data:", data);
        }}
      />
    </BoxEditor>
  );
};

export default TextEditor;
