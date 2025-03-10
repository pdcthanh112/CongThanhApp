package com.congthanh.productservice.model.response;

import java.util.List;

public record VariantValueResponse(String title, List<OptionValue> option) {
}
