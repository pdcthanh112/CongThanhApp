package com.congthanh.customerservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHistoryDTO {
    private long id;

    private String userId;

    private String keyword;

    private String searchTime;

}
