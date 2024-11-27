package com.congthanh.orderservice.model.mapper;

import com.congthanh.orderservice.model.dto.OrderItemDTO;
import com.congthanh.orderservice.model.entity.OrderItem;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper {

    private static  final  ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {

    }

    public static OrderItem mapOrderDetailDTOToEntity(OrderItemDTO orderItemDTO) {
        return modelMapper.map(orderItemDTO, OrderItem.class);
    }

    public static OrderItemDTO mapOrderDetailEntityToDTO(OrderItem orderItem) {
        return modelMapper.map(orderItem, OrderItemDTO.class);
    }

}
