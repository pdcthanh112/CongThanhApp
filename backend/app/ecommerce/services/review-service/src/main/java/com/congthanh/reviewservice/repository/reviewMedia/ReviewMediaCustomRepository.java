package com.congthanh.reviewservice.repository.reviewMedia;

import com.congthanh.reviewservice.model.entity.ReviewMedia;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewMediaCustomRepository {

    Optional<ReviewMedia> findByReviewMediaId(Long id);

    List<ReviewMedia> findByReviewId(Long id);
}
