//package com.congthanh.productservice.service.serviceImpl;
//
//import com.congthanh.productservice.model.viewmodel.ProductVm;
//import com.congthanh.productservice.service.ProductRedisService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ProductRedisServiceImpl implements ProductRedisService {
//
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    private final ObjectMapper reidsObjectMapper;
//
//    private String getKeyFrom(String keyword, PageRequest pageRequest) {
//        int page = pageRequest.getPageNumber();
//        int limit = pageRequest.getPageSize();
//        Sort sort = pageRequest.getSort();
//        String sortDirection = sort.getOrderFor("id").getDirection() == Sort.Direction.ASC ? "asc" : "desc";
//        return String.format("product:%d:%d:%s", page, limit, sortDirection);
//    }
//
//    @Override
//    public List<ProductVm> getAllProduct() {
//        return List.of();
//    }
//
//    @Override
//    public ProductVm getProductBySlug(String slug) {
//        return null;
//    }
//
//    @Override
//    public void saveAllProduct(List<ProductVm> products) throws JsonProcessingException {
//
//    }
//
//    @Override
//    public void clear() {
//        redisTemplate.getConnectionFactory().getConnection().flushAll();
//    }
//}
