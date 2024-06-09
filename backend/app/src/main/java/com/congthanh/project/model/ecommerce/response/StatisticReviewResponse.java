package com.congthanh.project.model.ecommerce.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticReviewResponse {

    private int totalReview;

    private float averageRating;

    private int reviewWithRating5;

    private int reviewWithRating4;

    private int reviewWithRating3;

    private int reviewWithRating2;

    private int reviewWithRating1;

    private int reviewWithMedia;

}
