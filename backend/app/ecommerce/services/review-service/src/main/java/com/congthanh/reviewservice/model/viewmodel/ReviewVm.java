package com.congthanh.reviewservice.model.viewmodel;

import com.congthanh.reviewservice.model.dto.ReviewMediaDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ReviewVm {

    private Long id;

    private String content;

    private int rating;

    @NotNull
    private String author;

    @NotNull
    private String product;

    private String variant;

    private List<ReviewMediaVm> reviewMedia;

    private Instant createdAt;

}
