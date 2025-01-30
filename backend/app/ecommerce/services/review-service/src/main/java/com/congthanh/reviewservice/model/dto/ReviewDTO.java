package com.congthanh.reviewservice.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {

    private String id;

    private String content;

    @Min(0)
    @Max(5)
    private int rating;

    @NotNull
    private String author;

    @NotNull
    private String product;

    private String variant;

    private List<ReviewMediaDTO> reviewMedia;

    private Instant createdAt;

}
