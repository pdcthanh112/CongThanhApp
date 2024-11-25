package com.congthanh.notificationservice.model.response;

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

    private int reviewRating5Star;

    private int reviewRating4Star;

    private int reviewRating3Star;

    private int reviewRating2Star;

    private int reviewRating1Star;

    private int reviewWithMedia;

}
