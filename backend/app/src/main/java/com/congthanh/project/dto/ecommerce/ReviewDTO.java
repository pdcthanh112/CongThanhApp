package com.congthanh.project.dto.ecommerce;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {

    private String id;

    private String content;

    @Min(0)
    @Max(5)
    private float rating;

    @NotNull
    private String customerId;

    @NotNull
    private String product;

    private long createdDate;

}
