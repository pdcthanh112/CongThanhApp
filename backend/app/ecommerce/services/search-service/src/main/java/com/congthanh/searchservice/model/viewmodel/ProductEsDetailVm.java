package com.congthanh.searchservice.model.viewmodel;

import java.math.BigDecimal;
import java.util.List;

public record ProductEsDetailVm(Long id,
                                String name,
                                String slug,
                                BigDecimal price,
                                boolean isPublished,
                                boolean isVisibleIndividually,
                                boolean isAllowedToOrder,
                                boolean isFeatured,
                                Long thumbnailMediaId,
                                String brand,
                                List<String> categories,
                                List<String> attributes) {
}
