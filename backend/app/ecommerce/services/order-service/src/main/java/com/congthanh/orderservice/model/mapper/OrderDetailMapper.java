package com.congthanh.orderservice.model.mapper;

import com.congthanh.orderservice.model.dto.OrderDetailDTO;
import com.congthanh.orderservice.model.entity.OrderDetail;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper {

    private static  final  ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {

    }

    public static OrderDetail mapOrderDetailDTOToEntity(OrderDetailDTO orderDetailDTO) {
        return modelMapper.map(orderDetailDTO, OrderDetail.class);
    }

    public static OrderDetailDTO mapOrderDetailEntityToDTO(OrderDetail orderDetail) {
        return modelMapper.map(orderDetail, OrderDetailDTO.class);
    }

}
