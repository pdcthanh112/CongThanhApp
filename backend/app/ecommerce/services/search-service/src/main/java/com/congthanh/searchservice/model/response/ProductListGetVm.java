package com.congthanh.searchservice.model.response;

import java.util.List;
import java.util.Map;

public record ProductListGetVm(List<ProductGetVm> products,
                               int pageNo,
                               int pageSize,
                               long totalElements,
                               int totalPages,
                               boolean isLast,
                               Map<String, Map<String, Long>> aggregations) {
}
