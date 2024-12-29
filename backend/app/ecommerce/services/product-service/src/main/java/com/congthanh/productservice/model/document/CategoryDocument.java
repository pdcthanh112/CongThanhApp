package com.congthanh.productservice.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDocument {

    private String id;

    private String name;

    private List<CategoryDocument> children;

}
