package com.congthanh.reviewservice.model.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "review")
@Data
@Builder
public class ReviewDocument {

    @Id
    private Long id;

    private String content;

    private int rating;

    private String author;

    private String product;

    private String variant;

    private List<ReviewMediaDocument> reviewMedia;

    private Instant createdAt;

}
