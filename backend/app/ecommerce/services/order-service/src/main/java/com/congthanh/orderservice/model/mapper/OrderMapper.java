package com.congthanh.orderservice.model.mapper;

import com.congthanh.orderservice.model.dto.OrderDTO;
import com.congthanh.orderservice.model.entity.Order;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

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

    public static Order mapOrderDTOToEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }

    public static OrderDTO mapOrderEntityToDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
}
