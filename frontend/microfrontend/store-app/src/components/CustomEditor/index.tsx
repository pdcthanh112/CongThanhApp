'use client';

import { CKEditor } from '@ckeditor/ckeditor5-react';
import { ClassicEditor, Essentials, Paragraph, Bold, Italic, Heading } from 'ckeditor5';
// import { FormatPainter } from 'ckeditor5-premium-features';

// import List from '@ckeditor/ckeditor5-list/src/list';
// import Link from '@ckeditor/ckeditor5-link/src/link';
// import Image from '@ckeditor/ckeditor5-image/src/image';
// import Alignment from '@ckeditor/ckeditor5-alignment/src/alignment';

import 'ckeditor5/ckeditor5.css';
import 'ckeditor5-premium-features/ckeditor5-premium-features.css';
import './style.css'

function CustomEditor() {
  return (
    <CKEditor
      editor={ClassicEditor}
      config={{
        licenseKey: 'GPL',
        plugins: [Essentials, Paragraph, Bold, Italic, Heading],
        toolbar: [
          'undo',
          'redo',
          '|',
          'bold',
          'italic',
          '|',
          'formatPainter',
          'heading',
          'numberedList',
          'bulletedList',
          'imageUpload',
        ],
        initialData: '<p>Product description</p>',
        heading: {
          options: [
            { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
            { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
            { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' },
          ],
        },
        fontFamily: {
          options: ['default', 'Ubuntu, Arial, sans-serif', 'Ubuntu Mono, Courier New, Courier, monospace'],
        },
        fontColor: {
          colorPicker: {
            format: 'hex',
          },
        },
        alignment: {
          options: ['left', 'center', 'right', 'justify'],
        },
      }}
    />
  );
}

export default CustomEditor;
