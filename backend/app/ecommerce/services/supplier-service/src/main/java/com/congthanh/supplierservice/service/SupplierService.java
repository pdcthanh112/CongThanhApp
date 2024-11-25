package com.congthanh.supplierservice.service;

import com.congthanh.supplierservice.model.dto.SupplierDTO;
import com.congthanh.supplierservice.model.entity.Supplier;
import com.congthanh.supplierservice.model.response.ProductResponse;
import com.congthanh.supplierservice.model.response.ResponseWithPagination;

public interface SupplierService {

    SupplierDTO getSupplierById(String id);

    SupplierDTO getSupplierBySlug(String slug);

    Supplier createSupplier(SupplierDTO supplierDTO);

    ResponseWithPagination<ProductResponse> getProductFromSupplier(String supplierId, Integer page, Integer limit);

}
