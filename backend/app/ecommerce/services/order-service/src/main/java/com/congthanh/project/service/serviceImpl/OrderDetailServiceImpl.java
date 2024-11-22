package com.congthanh.project.service.serviceImpl;

import com.congthanh.project.model.dto.OrderDetailDTO;
import com.congthanh.project.model.entity.Order;
import com.congthanh.project.model.entity.OrderDetail;
import com.congthanh.project.model.mapper.OrderDetailMapper;
import com.congthanh.project.model.request.CreateOrderDetailRequest;
import com.congthanh.project.model.response.PaginationInfo;
import com.congthanh.project.model.response.ResponseWithPagination;
import com.congthanh.project.model.dto.ProductResponse;
import com.congthanh.project.repository.orderDetail.OrderDetailRepository;
import com.congthanh.project.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetailDTO createOrderDetail(CreateOrderDetailRequest createOrderDetailRequest) {
        ProductResponse product = null;
        OrderDetail orderDetail = OrderDetail.builder()
                .product(product.getId())
                .quantity(createOrderDetailRequest.getQuantity())
                .orders(Order.builder().id(createOrderDetailRequest.getOrder()).build())
                .status("NEW")
                .build();
        OrderDetail result = orderDetailRepository.save(orderDetail);
        return OrderDetailMapper.mapOrderDetailEntityToDTO(result);
    }

    @Override
    public ResponseWithPagination<OrderDetailDTO> getOrderDetailByStatus(String status, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<OrderDetail> result = orderDetailRepository.findByStatus(status, pageRequest);
        if(result.hasContent()) {
            ResponseWithPagination<OrderDetailDTO> response = new ResponseWithPagination<>();
            List<OrderDetailDTO> list = new ArrayList<>();
            for(OrderDetail item: result) {
                OrderDetailDTO orderDTO = OrderDetailMapper.mapOrderDetailEntityToDTO(item);
                list.add(orderDTO);
            }
            PaginationInfo paginationInfo = PaginationInfo.builder()
                    .page(page)
                    .limit(limit)
                    .totalPage(result.getTotalPages())
                    .totalElement(result.getTotalElements())
                    .build();
            response.setResponseList(list);
            response.setPaginationInfo(paginationInfo);
            return response;
        }
        return null;
    }
}
