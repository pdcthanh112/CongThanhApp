package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.SupplierDTO;
import com.congthanh.project.entity.ecommerce.Supplier;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);
    }

    @PostConstruct
    private void configureModelMapper() {

    }

    public static Supplier mapSupplierDTOToEntity(SupplierDTO supplierDTO) {
        return modelMapper.map(supplierDTO, Supplier.class);
    }

    public static SupplierDTO mapSupplierEntityToDTO(Supplier store) {
        return modelMapper.map(store, SupplierDTO.class);
    }
}
