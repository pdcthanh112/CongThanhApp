package com.congthanh.mediaservice.repository;

import com.congthanh.mediaservice.model.entity.MediaFile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MediaFileCustomRepositoryImpl implements MediaFileCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insertMultipleFiles(List<MediaFile> mediaFile) {
        if (mediaFile == null || mediaFile.isEmpty()) {
            return;
        }

        StringBuilder sql = new StringBuilder("INSERT INTO media (id, file_name, file_path, media_type, reference_id) VALUES ");
        for (int i = 0; i < mediaFile.size(); i++) {
            MediaFile media = mediaFile.get(i);
            sql.append(String.format("(%d, '%s', '%s', '%s', %d)",
                    media.getId(),
                    media.getFileName().replace("'", "''"), // Escape single quotes
                    media.getFilePath().replace("'", "''"),
                    media.getMediaType().toString().replace("'", "''"),
                    media.getReferenceId()
            ));
            if (i < mediaFile.size() - 1) {
                sql.append(", ");
            }
        }

        entityManager.createNativeQuery(sql.toString()).executeUpdate();
    }
}
