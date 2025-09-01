package com.congthanh.mediaservice.repository;

import com.congthanh.mediaservice.model.entity.MediaFile;

import java.util.List;

public interface MediaFileCustomRepository {

    void insertMultipleFiles(List<MediaFile> mediaFile);
}
