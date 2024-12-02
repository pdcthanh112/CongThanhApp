package com.congthanh.searchservice.service;

import com.congthanh.searchservice.model.document.Product;
import com.congthanh.searchservice.model.request.QueryCriteria;
import com.congthanh.searchservice.model.response.ProductListGetVm;
import com.congthanh.searchservice.model.response.ProductNameListVm;

import java.util.List;

public interface SearchProductService {

    List<Product> searchProducts(String query);

    ProductListGetVm findProductAdvance(QueryCriteria productCriteria);

    ProductNameListVm autoCompleteProductName(final String keyword);
}
