package com.congthanh.searchservice.service;

public interface ProductSyncDataService {

    ProductEsDetailVm getProductEsDetailById(String id);

    public void createProduct(String id);

    public void updateProduct(String id);

    public void deleteProduct(String id);
}
