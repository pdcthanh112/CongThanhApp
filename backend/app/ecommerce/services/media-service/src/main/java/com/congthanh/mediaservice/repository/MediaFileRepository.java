package com.congthanh.mediaservice.repository;

import com.congthanh.mediaservice.model.entity.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Long>, MediaFileCustomRepository {
}
