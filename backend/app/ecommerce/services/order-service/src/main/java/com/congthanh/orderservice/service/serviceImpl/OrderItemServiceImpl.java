package com.congthanh.orderservice.service.serviceImpl;

import com.congthanh.orderservice.model.dto.OrderItemDTO;
import com.congthanh.orderservice.model.entity.Order;
import com.congthanh.orderservice.model.entity.OrderItem;
import com.congthanh.orderservice.model.mapper.OrderDetailMapper;
import com.congthanh.orderservice.model.request.CreateOrderDetailRequest;
import com.congthanh.orderservice.model.response.PaginationInfo;
import com.congthanh.orderservice.model.response.ResponseWithPagination;
import com.congthanh.orderservice.model.dto.ProductResponse;
import com.congthanh.orderservice.repository.orderItem.OrderItemRepository;
import com.congthanh.orderservice.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderDetailRepository;

    @Override
    public OrderItemDTO createOrderDetail(CreateOrderDetailRequest createOrderDetailRequest) {
        ProductResponse product = null;
        OrderItem orderItem = OrderItem.builder()
                .product(product.getId())
                .quantity(createOrderDetailRequest.getQuantity())
                .orders(Order.builder().id(createOrderDetailRequest.getOrder()).build())
                .status("NEW")
                .build();
        OrderItem result = orderDetailRepository.save(orderItem);
        return OrderDetailMapper.mapOrderDetailEntityToDTO(result);
    }

    @Override
    public ResponseWithPagination<OrderItemDTO> getOrderDetailByStatus(String status, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<OrderItem> result = orderDetailRepository.findByStatus(status, pageRequest);
        if(result.hasContent()) {
            ResponseWithPagination<OrderItemDTO> response = new ResponseWithPagination<>();
            List<OrderItemDTO> list = new ArrayList<>();
            for(OrderItem item: result) {
                OrderItemDTO orderDTO = OrderDetailMapper.mapOrderDetailEntityToDTO(item);
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
