// "use client";

// import React, { useCallback, useEffect, useRef, useState } from "react";
// import { X, File, Plus } from "lucide-react";
// import { cn } from "@/lib/utils";
// import { Button } from "@/components/ui/button";
// import Image from "next/image";

// // --- Types ---
// interface MultiImageUploadProps {
//   value?: File[];
//   onChange?: (files: File[]) => void;
//   maxImages?: number;
//   className?: string;
//   name?: string;
//   accept?: string;
// }

// interface ImagePreviewProps {
//   src: string;
//   alt?: string;
//   onDelete?: () => void;
//   fileType: string;
// }

// // --- Image Preview Component ---
// const ImagePreview: React.FC<ImagePreviewProps> = ({ src, alt, onDelete, fileType }) => {
//   const isImage = fileType.startsWith("image/");
//   return (
//     <div className="relative flex-shrink-0 w-20 h-20 sm:w-24 sm:h-24 rounded-md">
//       {isImage ? (
//         <Image src={src} alt={alt || "File preview"} className="w-full h-full object-cover rounded-md" loading="lazy" width={20} height={20}/>
//       ) : (
//         <div className="w-full h-full flex items-center justify-center bg-gray-100 rounded-md">
//           <File className="h-8 w-8 text-gray-500 sm:h-10 sm:w-10" />
//         </div>
//       )}
//       {onDelete && (
//         <button
//           onClick={onDelete}
//           className="absolute right-1 top-1 rounded-full bg-gray-200 p-1 text-gray-600 hover:bg-gray-300"
//           aria-label="Remove file"
//           type="button"
//         >
//           <X className="h-3 w-3 sm:h-4 sm:w-4" />
//         </button>
//       )}
//     </div>
//   );
// };

// // --- MultiImageUpload Component ---
// export const MultiImageUpload: React.FC<MultiImageUploadProps> = ({ value = [], onChange, maxImages, className, name, accept = "image/*" }) => {
//   const [files, setFiles] = useState<File[]>(value);
//   const prevValueRef = useRef<File[]>(value);

//   useEffect(() => {
//     if (JSON.stringify(value) !== JSON.stringify(prevValueRef.current)) {
//       setFiles(value);
//       prevValueRef.current = value;
//     }
//   }, [value]);

//   const handleUpload = useCallback(
//     (filesList: FileList) => {
//       const fileArray = Array.from(filesList).slice(0, maxImages ? maxImages - files.length : undefined);
//       if (fileArray.length === 0) return;
//       setFiles((prev) => [...prev, ...fileArray]);
//       onChange?.([...files, ...fileArray]);
//     },
//     [files, maxImages, onChange]
//   );

//   const handleDeleteImage = useCallback(
//     (index: number) => {
//       const newFiles = files.filter((_, i) => i !== index);
//       setFiles(newFiles);
//       onChange?.(newFiles);
//     },
//     [files, onChange]
//   );

//   return (
//     <div className={cn("flex flex-col w-full", className)}>
//       <div className="flex flex-wrap gap-4">
//         {(maxImages === undefined || files.length < maxImages) && (
//           <Button variant="outline" className="h-20 w-20 sm:h-24 sm:w-24 flex-shrink-0" asChild>
//             <label className="flex h-full w-full cursor-pointer items-center justify-center text-sm sm:text-base">
//               <Plus />
//               <input
//                 type="file"
//                 accept={accept}
//                 multiple
//                 className="hidden"
//                 name={name}
//                 onChange={(e) => e.target.files?.length && handleUpload(e.target.files)}
//               />
//             </label>
//           </Button>
//         )}
//         {files.map((file, index) => (
//           <ImagePreview
//             key={index}
//             src={URL.createObjectURL(file)}
//             alt={`File ${index}`}
//             fileType={file.type}
//             onDelete={() => handleDeleteImage(index)}
//           />
//         ))}
//       </div>
//     </div>
//   );
// };
'use client';

import React, { useCallback, useEffect, useRef, useState } from 'react';
import { X, File, Plus } from 'lucide-react';
import { cn } from '@/lib/utils';
import { Button } from '@/components/ui/button';
import Image from 'next/image';

// --- Types ---
interface MultiImageUploadProps {
  value?: File | File[];
  onChange?: (files: File[]) => void;
  maxImages?: number;
  className?: string;
  name?: string;
  accept?: string;
}

interface ImagePreviewProps {
  src: string;
  alt?: string;
  onDelete?: () => void;
  fileType: string;
}

// --- Image Preview Component ---
const ImagePreview: React.FC<ImagePreviewProps> = ({ src, alt, onDelete, fileType }) => {
  const isImage = fileType.startsWith('image/');
  return (
    <div className="relative flex-shrink-0 w-20 h-20 sm:w-24 sm:h-24 rounded-md">
      {isImage ? (
        <Image
          src={src}
          alt={alt || 'File preview'}
          className="w-full h-full object-cover rounded-md"
          loading="lazy"
          width={96}
          height={96}
        />
      ) : (
        <div className="w-full h-full flex items-center justify-center bg-gray-100 rounded-md">
          <File className="h-8 w-8 text-gray-500 sm:h-10 sm:w-10" />
        </div>
      )}
      {onDelete && (
        <button
          onClick={onDelete}
          className="absolute right-1 top-1 rounded-full bg-gray-200 p-1 text-gray-600 hover:bg-gray-300"
          aria-label="Remove file"
          type="button"
        >
          <X className="h-3 w-3 sm:h-4 sm:w-4" />
        </button>
      )}
    </div>
  );
};

// --- MultiImageUpload Component ---
export const MultiImageUpload: React.FC<MultiImageUploadProps> = ({
  value,
  onChange,
  maxImages,
  className,
  name,
  accept = 'image/*',
}) => {
  // Normalize the initial value to always be an array
  const normalizeValue = (val: File | File[] | undefined): File[] => {
    if (!val) return [];
    return Array.isArray(val) ? val : [val];
  };

  const [files, setFiles] = useState<File[]>(normalizeValue(value));
  const prevValueRef = useRef<File | File[] | undefined>(value);

  useEffect(() => {
    // Check if value has changed by comparing string representation
    if (
      JSON.stringify(prevValueRef.current) !== JSON.stringify(value) ||
      // Also handle the case where a single File was replaced with another single File
      (!Array.isArray(prevValueRef.current) && !Array.isArray(value) && prevValueRef.current !== value)
    ) {
      setFiles(normalizeValue(value));
      prevValueRef.current = value;
    }
  }, [value]);

  const handleUpload = useCallback(
    (filesList: FileList) => {
      const remainingSlots = maxImages ? maxImages - files.length : undefined;
      if (remainingSlots !== undefined && remainingSlots <= 0) return;

      const fileArray = Array.from(filesList).slice(0, remainingSlots);
      if (fileArray.length === 0) return;

      const newFiles = [...files, ...fileArray];
      setFiles(newFiles);
      onChange?.(newFiles);
    },
    [files, maxImages, onChange]
  );

  const handleDeleteImage = useCallback(
    (index: number) => {
      const newFiles = files.filter((_, i) => i !== index);
      setFiles(newFiles);
      onChange?.(newFiles);
    },
    [files, onChange]
  );

  return (
    <div className={cn('flex flex-col w-full', className)}>
      <div className="flex flex-wrap gap-4">
        {(maxImages === undefined || files.length < maxImages) && (
          <Button variant="outline" className="h-20 w-20 sm:h-24 sm:w-24 flex-shrink-0" asChild>
            <label className="flex h-full w-full cursor-pointer items-center justify-center text-sm sm:text-base">
              <Plus />
              <input
                type="file"
                accept={accept}
                multiple
                className="hidden"
                name={name}
                onChange={(e) => e.target.files?.length && handleUpload(e.target.files)}
              />
            </label>
          </Button>
        )}
        {files.map((file, index) => (
          <ImagePreview
            key={`file-${index}-${file.name}`}
            src={URL.createObjectURL(file)}
            alt={`File ${index}`}
            fileType={file.type}
            onDelete={() => handleDeleteImage(index)}
          />
        ))}
      </div>
    </div>
  );
};
