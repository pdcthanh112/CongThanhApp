package com.congthanh.project.service.ecommerce;

import com.congthanh.project.dto.ecommerce.ProductDTO;
import com.congthanh.project.dto.ecommerce.SupplierDTO;
import com.congthanh.project.entity.ecommerce.Supplier;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;

public interface SupplierService {

    SupplierDTO getSupplierById(String id);

    SupplierDTO getSupplierBySlug(String slug);

    Supplier createSupplier(SupplierDTO supplierDTO);

    ResponseWithPagination<ProductDTO> getProductFromSupplier(String storeId, Integer page, Integer limit);

}
