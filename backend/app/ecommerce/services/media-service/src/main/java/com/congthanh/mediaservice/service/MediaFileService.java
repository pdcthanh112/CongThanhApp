package com.congthanh.mediaservice.service;

import com.congthanh.mediaservice.model.dto.MediaFileDTO;
import com.congthanh.mediaservice.model.request.UploadMediaRequest;

import java.util.List;

public interface MediaFileService {

    List<MediaFileDTO> uploadMediaFile(UploadMediaRequest[] request);
}
