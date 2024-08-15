package com.congthanh.project.model.mapper;

import com.congthanh.project.dto.InventoryDTO;
import com.congthanh.project.entity.Inventory;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {

    }

    public static Inventory mapCartDTOToEntity(InventoryDTO cartDTO) {
        return modelMapper.map(cartDTO, Inventory.class);
    }

    public static InventoryDTO mapCartEntityToDTO(Inventory cart) {
        return modelMapper.map(cart, InventoryDTO.class);
    }

}
