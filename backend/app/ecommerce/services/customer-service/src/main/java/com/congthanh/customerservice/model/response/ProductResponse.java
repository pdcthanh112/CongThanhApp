package com.congthanh.customerservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private String id;

    private String name;

    private String category;

    private String subcategory;

    private String description;

    private String status;

    private String slug;

}
