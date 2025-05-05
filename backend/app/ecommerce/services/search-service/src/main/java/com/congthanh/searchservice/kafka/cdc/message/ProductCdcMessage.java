package com.congthanh.searchservice.kafka.cdc.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCdcMessage {

    private Product after;

    private Product before;

    private Operation op;
}
