package com.congthanh.supplierservice.service.serviceImpl;

import com.congthanh.supplierservice.model.dto.SupplierDTO;
import com.congthanh.supplierservice.model.entity.Supplier;
import com.congthanh.supplierservice.model.mapper.SupplierMapper;
import com.congthanh.supplierservice.model.response.PaginationInfo;
import com.congthanh.supplierservice.model.response.ProductResponse;
import com.congthanh.supplierservice.model.response.ResponseWithPagination;
import com.congthanh.supplierservice.repository.SupplierRepository;
import com.congthanh.supplierservice.service.SupplierService;
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
    public ResponseWithPagination<ProductResponse> getProductFromSupplier(String storeId, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<ProductResponse> result = supplierRepository.getProductFromSupplier(storeId, pageable);
        ResponseWithPagination<ProductResponse> response = new ResponseWithPagination<>();
        if (result.hasContent()) {
            List<ProductResponse> list = new ArrayList<>();
            for (ProductResponse product : result.getContent()) {
                list.add(product);
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
