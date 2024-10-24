package com.congthanh.project.service;

import com.congthanh.project.dto.SupplierDTO;
import com.congthanh.project.entity.Supplier;
import com.congthanh.project.model.response.ProductResponse;
import com.congthanh.project.model.response.ResponseWithPagination;

public interface SupplierService {

    SupplierDTO getSupplierById(String id);

    SupplierDTO getSupplierBySlug(String slug);

    Supplier createSupplier(SupplierDTO supplierDTO);

    ResponseWithPagination<ProductResponse> getProductFromSupplier(String supplierId, Integer page, Integer limit);

}
