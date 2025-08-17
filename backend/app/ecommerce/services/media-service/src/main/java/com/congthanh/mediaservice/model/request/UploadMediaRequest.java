package com.congthanh.mediaservice.model.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UploadMediaRequest(String caption,
                                 @NotNull
                                 @ValidFileType(allowedTypes = {"image/jpeg", "image/png", "image/gif"},
                                         message = "File type not allowed. Allowed types are: JPEG, PNG, GIF")
                                 MultipartFile multipartFile,
                                 String fileNameOverride) {
}
