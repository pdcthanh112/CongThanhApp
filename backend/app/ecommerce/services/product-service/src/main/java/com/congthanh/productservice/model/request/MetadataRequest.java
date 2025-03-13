package com.congthanh.productservice.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetadataRequest {

    private String title;

    private String keywords;

    private String description;
}
