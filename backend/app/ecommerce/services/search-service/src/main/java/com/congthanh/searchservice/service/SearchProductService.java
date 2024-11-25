package com.congthanh.searchservice.service;

import com.congthanh.searchservice.model.request.QueryCriteria;
import com.congthanh.searchservice.model.response.ProductListGetVm;
import com.congthanh.searchservice.model.response.ProductNameListVm;

public interface SearchProductService {

    ProductListGetVm findProductAdvance(QueryCriteria productCriteria);

    ProductNameListVm autoCompleteProductName(final String keyword);
}
