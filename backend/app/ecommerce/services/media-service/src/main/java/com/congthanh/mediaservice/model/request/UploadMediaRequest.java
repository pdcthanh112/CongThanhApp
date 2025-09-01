package com.congthanh.mediaservice.model.request;

import com.congthanh.mediaservice.utils.ValidFileType;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UploadMediaRequest(
        @NotNull
        @ValidFileType(allowedTypes = {"image/jpeg", "image/jpg", "image/png", "image/gif"},
                message = "File type not allowed. Allowed types are: JPEG, PNG, GIF")
        MultipartFile multipartFile,
        String fileName) {
}
