package com.congthanh.reviewservice.model.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document
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

class ReviewMediaDocument {

    private Long id;

    private String url;

}
