package com.congthanh.project.model.mapper;

import com.congthanh.project.dto.ecommerce.OrderDTO;
import com.congthanh.project.entity.Order;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureModelMapper() {

    }

    public Order mapOrderDTOToEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }

    public OrderDTO mapOrderEntityToDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
}
