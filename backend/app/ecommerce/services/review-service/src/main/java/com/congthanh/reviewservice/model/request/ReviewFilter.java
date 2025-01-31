package com.congthanh.reviewservice.model.request;

import lombok.Builder;

@Builder
public record ReviewFilter (int page , int limit, int rating, boolean hasMedia) { }
