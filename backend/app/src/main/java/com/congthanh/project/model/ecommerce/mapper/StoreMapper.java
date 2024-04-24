package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.StoreDTO;
import com.congthanh.project.entity.ecommerce.Store;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

    }

    public Store mapStoreDTOToEntity(StoreDTO storeDTO) {
        return modelMapper.map(storeDTO, Store.class);
    }

    public StoreDTO mapStoreEntityToDTO(Store store) {
        return modelMapper.map(store, StoreDTO.class);
    }
}
