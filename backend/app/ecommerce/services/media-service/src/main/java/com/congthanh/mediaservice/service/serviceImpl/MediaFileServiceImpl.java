package com.congthanh.mediaservice.service.serviceImpl;

import com.congthanh.mediaservice.constant.enums.MediaType;
import com.congthanh.mediaservice.model.dto.MediaFileDTO;
import com.congthanh.mediaservice.model.entity.MediaFile;
import com.congthanh.mediaservice.model.request.UploadMediaRequest;
import com.congthanh.mediaservice.repository.MediaFileRepository;
import com.congthanh.mediaservice.service.AwsS3Service;
import com.congthanh.mediaservice.service.MediaFileService;
import com.congthanh.mediaservice.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaFileServiceImpl implements MediaFileService {

    private final AwsS3Service awsS3Service;

    private final MediaFileRepository mediaFileRepository;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public List<MediaFileDTO> uploadMediaFile(UploadMediaRequest[] request) {
        if (request == null || request.length == 0) {
            return List.of(new MediaFileDTO());
        }

        List<MediaFileDTO> result = new ArrayList<>();
        List<MediaFile> mediaFiles = new ArrayList<>();
        for (UploadMediaRequest requestItem : request) {
            String item = awsS3Service.uploadFile(requestItem.multipartFile());

            MediaFile mediaFile = MediaFile.builder()
                    .id(snowflakeIdGenerator.nextId())
                    .fileName(requestItem.fileName())
                    .filePath(item)
                    .mediaType(MediaType.PRODUCT)
                    .build();

            mediaFiles.add(mediaFile);
        }
        mediaFileRepository.insertMultipleFiles(mediaFiles);
        return result;
    }
}
