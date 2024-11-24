package com.congthanh.project.service;

import com.congthanh.project.model.request.QueryCriteria;
import com.congthanh.project.model.response.ProductListGetVm;
import com.congthanh.project.model.response.ProductNameListVm;

public interface SearchProductService {

    ProductListGetVm findProductAdvance(QueryCriteria productCriteria);

    ProductNameListVm autoCompleteProductName(final String keyword);
}
