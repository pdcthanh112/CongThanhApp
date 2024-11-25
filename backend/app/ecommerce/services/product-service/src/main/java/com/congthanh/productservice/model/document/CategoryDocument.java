package com.congthanh.productservice.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDocument {

    private String id;

    private String name;

    private com.congthanh.catalogservice.model.document.CategoryDocument subcategory;

}
