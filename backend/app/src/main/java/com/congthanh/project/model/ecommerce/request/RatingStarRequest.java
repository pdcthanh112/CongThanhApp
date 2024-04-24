package com.congthanh.project.model.ecommerce.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingStarRequest {

    @Min(0)
    private Long vote;

    @Min(0)
    private Double value;

}
