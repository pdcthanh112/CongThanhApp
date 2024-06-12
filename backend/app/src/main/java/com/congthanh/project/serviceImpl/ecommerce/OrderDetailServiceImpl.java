package com.congthanh.project.serviceImpl.ecommerce;

import com.congthanh.project.dto.ecommerce.OrderDetailDTO;
import com.congthanh.project.entity.ecommerce.OrderDetail;
import com.congthanh.project.entity.ecommerce.Product;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.ecommerce.mapper.OrderDetailMapper;
import com.congthanh.project.model.ecommerce.request.CreateOrderDetailRequest;
import com.congthanh.project.model.ecommerce.response.PaginationInfo;
import com.congthanh.project.model.ecommerce.response.ResponseWithPagination;
import com.congthanh.project.repository.ecommerce.orderDetail.OrderDetailRepository;
import com.congthanh.project.repository.ecommerce.product.ProductRepository;
import com.congthanh.project.service.ecommerce.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public OrderDetailDTO createOrderDetail(CreateOrderDetailRequest createOrderDetailRequest) {
        Product product = productRepository.findById(createOrderDetailRequest.getProductId()).orElseThrow(() -> new NotFoundException("Product not found"));
        OrderDetail orderDetail = OrderDetail.builder()
                .product(product)
                .quantity(createOrderDetailRequest.getQuantity())
                .orders(createOrderDetailRequest.getOrder())
                .status("NEW")
                .build();
        OrderDetail result = orderDetailRepository.save(orderDetail);
        return orderDetailMapper.mapOrderDetailEntityToDTO(result);
    }

    @Override
    public ResponseWithPagination<OrderDetailDTO> getOrderDetailByStatus(String status, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<OrderDetail> result = orderDetailRepository.findByStatus(status, pageRequest);
        if(result.hasContent()) {
            ResponseWithPagination<OrderDetailDTO> response = new ResponseWithPagination<>();
            List<OrderDetailDTO> list = new ArrayList<>();
            for(OrderDetail item: result) {
                OrderDetailDTO orderDTO = orderDetailMapper.mapOrderDetailEntityToDTO(item);
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
