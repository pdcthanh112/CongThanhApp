package com.congthanh.reviewservice.repository.reviewMedia;

import com.congthanh.reviewservice.model.entity.ReviewMedia;

import java.util.List;
import java.util.Optional;

public class ReviewMediaCustomRepositoryImpl implements ReviewMediaCustomRepository{
    @Override
    public Optional<ReviewMedia> findByReviewMediaId(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ReviewMedia> findByReviewId(Long id) {
        return List.of();
    }
}
