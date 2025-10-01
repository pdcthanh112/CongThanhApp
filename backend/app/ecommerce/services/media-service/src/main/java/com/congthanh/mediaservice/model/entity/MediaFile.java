package com.congthanh.mediaservice.model.entity;

import com.congthanh.mediaservice.constant.enums.MediaType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "media_files")
public class MediaFile {

    @Id
    private Long id;

    private String fileName;

    private String filePath;

    private MediaType mediaType;

    private Long referenceId;

    private int displayOrder;
}
