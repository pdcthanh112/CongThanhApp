package com.congthanh.mediaservice.model.dto;

import com.congthanh.mediaservice.constant.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaFileDTO {

    private Long id;

    private String fileName;

    private String filePath;

    private MediaType mediaType;

    private Long referenceId;
}
