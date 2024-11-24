package com.congthanh.project.model.request;

import com.congthanh.project.constant.enums.SortType;

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
