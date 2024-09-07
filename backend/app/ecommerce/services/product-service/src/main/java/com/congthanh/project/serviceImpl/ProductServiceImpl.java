package com.congthanh.project.serviceImpl;

import com.congthanh.project.constant.enums.ProductStatus;
import com.congthanh.project.dto.*;
import com.congthanh.project.entity.Product;
import com.congthanh.project.model.request.CreateProductRequest;
import com.congthanh.project.model.response.*;
import com.congthanh.project.exception.ecommerce.NotFoundException;
import com.congthanh.project.model.mapper.ProductMapper;
import com.congthanh.project.repository.product.ProductRepository;
import com.congthanh.project.service.ProductService;
import com.congthanh.project.utils.Helper;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final KafkaTemplate<String, ProductDTO> kafkaTemplate;

    private final Helper helper = new Helper();

    @Override
    public Object getAllProduct(Integer page, Integer limit) {
        if (page != null & limit != null) {
            Pageable pageable = PageRequest.of(page, limit);
            Page<Product> result = productRepository.findAll(pageable);

            if (result.hasContent()) {
                ResponseWithPagination<ProductDTO> response = new ResponseWithPagination<>();
                List<ProductDTO> list = new ArrayList<>();
                for (Product product : result.getContent()) {
                    ProductDTO productDTO = ProductMapper.mapProductEntityToDTO(product);
                    list.add(productDTO);
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
            } else {
                throw new RuntimeException("List empty exception");
            }

        } else {
            List<Product> list = productRepository.findAll();
            List<ProductDTO> result = new ArrayList<>();
            for (Product product : list) {
                ProductDTO productDTO = ProductMapper.mapProductEntityToDTO(product);
                result.add(productDTO);
            }
            return result;
        }
    }   

    @Override
    public ProductDTO getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        ProductDTO response = ProductMapper.mapProductEntityToDTO(product);
        return response;
    }

    @Override
    public ProductDTO getProductDetailById(String id) {
        Product product = productRepository.getProductDetailById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        ProductDTO response = ProductMapper.mapProductEntityToDTO(product);
        return response;
    }

    @Override
    public ProductDTO getProductBySlug(String slug) {
        Product product = productRepository.findProductBySlug(slug).orElseThrow(() -> new NotFoundException("Product not found"));
        ProductDTO response = ProductMapper.mapProductEntityToDTO(product);
        return response;
    }

    @Override
    public ProductDTO createProduct(CreateProductRequest request) {
        Optional<Product> existProduct = productRepository.findByName(request.getName());
        if (existProduct.isPresent()) {
            throw new RuntimeException("Product ton taiii");
        } else {
            CategoryResponse category = null;
            SubcategoryResponse subcategory = null;
            SupplierResponse supplier = null;
            BrandResponse brand = null;

            String productSlug = helper.generateSlug(request.getName());

            assert category != null && subcategory != null && supplier != null && brand != null;
            Product product = Product.builder()
                    .name(request.getName())
                    .category(category.getId())
                    .subcategory(subcategory.getId())
                    .description(request.getDescription())
                    .brand(brand.getId())
                    .status(ProductStatus.ACTIVE)
                    .slug(productSlug)
                    .supplier(supplier.getId())
                    .build();
            Product savedProduct = productRepository.save(product);
            ProductDTO response = ProductMapper.mapProductEntityToDTO(savedProduct);

            kafkaTemplate.send("create-product-topic", response);

            return response;
        }
    }

    @Override
    public Product updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId()).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(productDTO.getName());
//     product.setCategory(productDTO.getCategory());
//     product.setSubcategory(productDTO.getSubcategory());
        product.setDescription(productDTO.getDescription());

        productRepository.save(product);
        return product;
    }

    @Override
    public boolean deleteProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        if (product.getStatus() == ProductStatus.DELETED) {
            throw new RuntimeException("Product have deleted before");
        } else {
            boolean result = productRepository.deleteProduct(id);
            return result;
        }
    }

//    @Override
//    public ResponseWithPagination<ProductDTO> getProductByCategory(int categoryId, int page, int limit) {
//        Pageable pageable = PageRequest.of(page, limit);
//        Page<Product> result = productRepository.findByCategoryId(categoryId, pageable);
//        ResponseWithPagination<ProductDTO> response = new ResponseWithPagination<>();
//        if (result.hasContent()) {
//            List<ProductDTO> list = new ArrayList<>();
//            for (Product product : result.getContent()) {
//                ProductDTO productDTO = ProductMapper.mapProductEntityToDTO(product);
//                list.add(productDTO);
//            }
//
//            PaginationInfo paginationInfo = PaginationInfo.builder()
//                    .page(page)
//                    .limit(limit)
//                    .totalPage(result.getTotalPages())
//                    .totalElement(result.getTotalElements())
//                    .build();
//            response.setResponseList(list);
//            response.setPaginationInfo(paginationInfo);
//        } else {
//            throw new RuntimeException("List empty exception");
//        }
//        return response;
//    }
//
//    @Override
//    public ResponseWithPagination<ProductDTO> getProductBySubcategory(int subcategoryId, int page, int limit) {
//        Pageable pageable = PageRequest.of(page, limit);
//        Page<Product> result = productRepository.findBySubcategoryId(subcategoryId, pageable);
//        ResponseWithPagination<ProductDTO> response = new ResponseWithPagination<>();
//        if (result.hasContent()) {
//            List<ProductDTO> list = new ArrayList<>();
//            for (Product product : result.getContent()) {
//                ProductDTO productDTO = ProductMapper.mapProductEntityToDTO(product);
//                list.add(productDTO);
//            }
//            PaginationInfo paginationInfo = PaginationInfo.builder()
//                    .page(page)
//                    .limit(limit)
//                    .totalPage(result.getTotalPages())
//                    .totalElement(result.getTotalElements())
//                    .build();
//            response.setResponseList(list);
//            response.setPaginationInfo(paginationInfo);
//        } else {
//            throw new RuntimeException("List empty exception");
//        }
//        return response;
//    }

    @Override
    public List<ProductDTO> searchProduct(String keyword) {
        List<Product> data = productRepository.searchProduct(keyword);
        System.out.println("CHECKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK" + data);

        if (data.size() > 0) {
            List<ProductDTO> result = new ArrayList<>();
            for (Product product : data) {
                ProductDTO productDTO = ProductMapper.mapProductEntityToDTO(product);
                result.add(productDTO);
            }
            return result;
        } else {
            return null;
        }
    }

    @Override
    public Long getSoldByProduct(String productId) {
        Long result = productRepository.countTotalSoldProduct(productId);
        return result != null ? result : 0;
    }

    @Override
    public List<ProductVariantAttributeValueResponse> getVariantAttributeValueByProduct(String productId) {
        List<Tuple> data = (List<Tuple>) productRepository.getVariantAttributeValueByProduct(productId);
        if(!data.isEmpty()){
            Map<String, ProductVariantAttributeValueResponse> responseMap = new HashMap<>();
            long idCounter = 1;

            for (Tuple row : data) {
                String attributeName = row.get("variantAttributeName", String.class);
                String attributeValue = row.get("variantAttributeValue", String.class);

                if (!responseMap.containsKey(attributeName)) {
                    ProductVariantAttributeValueResponse response = new ProductVariantAttributeValueResponse();
                    response.setId(idCounter++);
                    response.setAttributeName(attributeName);
                    response.setValue(new ArrayList<>());
                    responseMap.put(attributeName, response);
                }

                ProductVariantAttributeValueResponse.Value value = new ProductVariantAttributeValueResponse.Value();
                value.setId(Long.valueOf(row.get("variantAttributeId", Integer.class)));
                value.setValue(attributeValue);

                responseMap.get(attributeName).getValue().add(value);
            }

            return new ArrayList<>(responseMap.values());
        }
        return null;
    }
}

