package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.ProductDTO;
import com.congthanh.project.dto.ecommerce.SupplierDTO;
import com.congthanh.project.entity.ecommerce.Supplier;
import com.congthanh.project.model.ecommerce.mapper.ProductMapper;
import com.congthanh.project.model.ecommerce.mapper.SupplierMapper;
import com.congthanh.project.model.ecommerce.response.PaginationInfo;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.entity.ecommerce.Product;
import com.congthanh.project.repository.ecommerce.supplier.SupplierRepository;
import com.congthanh.project.service.ecommerce.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public SupplierDTO getSupplierById(String id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));
        SupplierDTO response = SupplierDTO.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .avatar(supplier.getAvatar())
                .background(supplier.getBackground())
                .slug(supplier.getSlug())
                .build();
        return response;
    }

    @Override
    public SupplierDTO getSupplierBySlug(String slug) {
        Supplier result = supplierRepository.getSupplierBySlug(slug);
        return SupplierMapper.mapSupplierEntityToDTO(result);
    }

    @Override
    public Supplier createSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = Supplier.builder()
                .name(supplierDTO.getName())
                .avatar(supplierDTO.getAvatar())
                .background(supplierDTO.getBackground())
                .slug(supplierDTO.getSlug())
                .build();
        return supplierRepository.save(supplier);
    }

    @Override
    public ResponseWithPagination<ProductDTO> getProductFromSupplier(String storeId, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Product> result = supplierRepository.getProductFromSupplier(storeId, pageable);
        ResponseWithPagination<ProductDTO> response = new ResponseWithPagination<>();
        if (result.hasContent()) {
            List<ProductDTO> list = new ArrayList<>();
            for (Product product : result.getContent()) {
                ProductDTO productDTO = ProductMapper.mapProductEntityToDTO(product);
                list.add(productDTO);
            }
            PaginationInfo paginationInfo = PaginationInfo.builder()
                    .page(page)
                    .limit(limit)
                    .totalPage(result.getTotalPages())
                    .totalElement(result.getTotalElements())
                    .build();
            response.setResponseList(list);
            response.setPaginationInfo(paginationInfo);
        } else {
            throw new RuntimeException("List empty exception");
        }
        return response;
    }

}
