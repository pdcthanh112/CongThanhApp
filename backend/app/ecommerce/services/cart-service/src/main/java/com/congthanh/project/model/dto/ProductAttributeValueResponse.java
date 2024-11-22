package com.congthanh.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttributeValueResponse {
    private Long id;
    private String attributeName;
    private List<Value> value;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Value {
        private Long id;
        private String value;
    }

}
