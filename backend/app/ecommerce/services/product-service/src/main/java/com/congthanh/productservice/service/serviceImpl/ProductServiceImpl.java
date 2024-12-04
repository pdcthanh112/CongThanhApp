package com.congthanh.productservice.service.serviceImpl;

import com.congthanh.catalogservice.grpc.BrandResponse;
import com.congthanh.catalogservice.grpc.CategoryGrpcService;
import com.congthanh.catalogservice.grpc.CategoryResponse;
import com.congthanh.catalogservice.model.response.ResponseWithPagination;
import com.congthanh.productservice.constant.enums.ProductStatus;
import com.congthanh.productservice.cqrs.command.command.CreateProductCommand;
import com.congthanh.productservice.cqrs.query.query.GetProductByIdQuery;
import com.congthanh.productservice.model.dto.ProductDTO;
import com.congthanh.productservice.model.dto.ProductVariantAttributeValueDTO;
import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.model.document.ProductDocument;
import com.congthanh.productservice.model.request.CreateProductRequest;
import com.congthanh.catalogservice.exception.ecommerce.NotFoundException;
import com.congthanh.productservice.model.mapper.ProductMapper;
import com.congthanh.productservice.repository.product.ProductRepository;
import com.congthanh.productservice.service.ProductAttributeService;
import com.congthanh.productservice.service.ProductService;
import com.congthanh.productservice.service.ProductVariantService;
import com.congthanh.catalogservice.utils.Helper;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.cache.annotation.Cacheable;
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
    private final ProductVariantService productVariantService;
    private final ProductAttributeService productAttributeService;
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final KafkaTemplate<String, ProductDTO> kafkaTemplate;
    private final CategoryGrpcService categoryGrpcService;

    @Override
    @Cacheable("products")
    public Object getAllProduct(Integer page, Integer limit) {
        if (page != null & limit != null) {
            Pageable pageable = PageRequest.of(page, limit);
            Page<Product> result = productRepository.findAll(pageable);

            if (result.hasContent()) {
                com.congthanh.catalogservice.model.response.ResponseWithPagination<ProductDTO> response = new ResponseWithPagination<>();
                List<ProductDTO> list = new ArrayList<>();
                for (Product product : result.getContent()) {
                    ProductDTO productDTO = ProductMapper.mapProductEntityToDTO(product);
                    list.add(productDTO);
                }

                com.congthanh.catalogservice.model.response.PaginationInfo paginationInfo = com.congthanh.catalogservice.model.response.PaginationInfo.builder()
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
    @Cacheable(cacheNames = "products", key = "#id")
    public ProductDTO getProductById(String id) {
        ProductDocument data = queryGateway.query(new GetProductByIdQuery(id), ResponseTypes.instanceOf(ProductDocument.class)).join();
        return ProductDTO.builder()
                .id(data.getId())
                .name(data.getName())
                .description(data.getDescription())
                .slug(data.getSlug())
//                .category(Category.build())
                .status(data.getStatus())
                .build();
    }

    @Override
    public ProductDTO getProductDetailById(String id) {
        Product product = productRepository.getProductDetailById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        ProductDTO response = ProductMapper.mapProductEntityToDTO(product);
        return response;
    }

    @Override
    public ProductDTO getProductBySlug(String slug) {
//        ProductQuery data = queryGateway.query(new GetProductBySlugQuery(slug), ResponseTypes.instanceOf(ProductQuery.class)).join();
//        return ProductDTO.builder()
//                .id(data.getId())
//                .name(data.getName())
//                .slug(data.getSlug())
//                .build();
        return null;
    }

    @Override
    public ProductDTO createProduct(CreateProductRequest request) {
        CategoryResponse category = categoryGrpcService.getCategoryById(request.getCategory());
//        SupplierResponse supplier = null;
        BrandResponse brand = null;

        String productSlug = Helper.generateSlug(request.getName());

//        assert category != null && subcategory != null && supplier != null && brand != null;
        CreateProductCommand mainProduct = CreateProductCommand.builder()
                .name(request.getName())
                .category(category.getId())
                .description(request.getDescription())
                .brand(String.valueOf(brand.getId()))
                .status(ProductStatus.ACTIVE)
                .slug(productSlug)
//                .supplier(supplier.getId())
                .build();
        ProductDTO result = commandGateway.sendAndWait(mainProduct);
        kafkaTemplate.send("create-product-topic", result);
        return result;

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
    public List<ProductVariantAttributeValueDTO> getVariantAttributeValueByProduct(String productId) {
        List<Tuple> data = (List<Tuple>) productRepository.getVariantAttributeValueByProduct(productId);
        if (!data.isEmpty()) {
            Map<String, ProductVariantAttributeValueDTO> responseMap = new HashMap<>();
            long idCounter = 1;

            for (Tuple row : data) {
                String attributeName = row.get("variantAttributeName", String.class);
                String attributeValue = row.get("variantAttributeValue", String.class);

                if (!responseMap.containsKey(attributeName)) {
                    ProductVariantAttributeValueDTO response = new ProductVariantAttributeValueDTO();
                    response.setId(idCounter++);
                    response.setAttributeName(attributeName);
                    response.setValue(new ArrayList<>());
                    responseMap.put(attributeName, response);
                }

                ProductVariantAttributeValueDTO.Value value = new ProductVariantAttributeValueDTO.Value();
                value.setId(Long.valueOf(row.get("variantAttributeId", Integer.class)));
                value.setValue(attributeValue);

                responseMap.get(attributeName).getValue().add(value);
            }

            return new ArrayList<>(responseMap.values());
        }
        return null;
    }

}

