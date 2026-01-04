package com.congthanh.productservice.model.viewmodel;

import org.springframework.web.multipart.MultipartFile;

public record UploadMediaMessage(String fileName, String mediaType, Long referenceId, MultipartFile file,
                                 int displayOrder) {
}
