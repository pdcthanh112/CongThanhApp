package com.congthanh.project.model.ecommerce.mapper;

import com.congthanh.project.dto.ecommerce.OrderDetailDTO;
import com.congthanh.project.entity.ecommerce.OrderDetail;
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
