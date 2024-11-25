package com.congthanh.searchservice.model.request;

import com.congthanh.searchservice.constant.enums.SortType;

public record QueryCriteria(String keyword,
                            Integer page,
                            Integer size,
                            String brand,
                            String category,
                            String attribute,
                            Double minPrice,
                            Double maxPrice,
                            SortType sortType) {
}
