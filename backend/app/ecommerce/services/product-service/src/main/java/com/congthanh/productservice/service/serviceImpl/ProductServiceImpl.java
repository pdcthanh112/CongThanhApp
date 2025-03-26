package com.congthanh.productservice.service.serviceImpl;

import com.congthanh.catalogservice.grpc.CategoryResponse;
import com.congthanh.productservice.cqrs.query.query.GetProductBySlugQuery;
import com.congthanh.productservice.grpc.client.CategoryGrpcClient;
import com.congthanh.productservice.model.entity.ProductAttributeValue;
import com.congthanh.productservice.model.entity.ProductCategory;
import com.congthanh.productservice.model.entity.ProductImage;
import com.congthanh.productservice.model.request.ProductImageRequest;
import com.congthanh.productservice.model.response.PaginationInfo;
import com.congthanh.productservice.model.response.ResponseWithPagination;
import com.congthanh.productservice.constant.enums.ProductStatus;
import com.congthanh.productservice.cqrs.command.command.CreateProductCommand;
import com.congthanh.productservice.cqrs.query.query.GetProductByIdQuery;
import com.congthanh.productservice.model.dto.ProductDTO;
import com.congthanh.productservice.model.entity.Product;
import com.congthanh.productservice.model.document.ProductDocument;
import com.congthanh.productservice.model.request.CreateProductRequest;
import com.congthanh.productservice.exception.ecommerce.NotFoundException;
import com.congthanh.productservice.model.mapper.ProductMapper;
import com.congthanh.productservice.model.viewmodel.ProductAttributeVm;
import com.congthanh.productservice.model.viewmodel.ProductDetailVm;
import com.congthanh.productservice.model.viewmodel.ProductVm;
import com.congthanh.productservice.repository.product.ProductRepository;
import com.congthanh.productservice.repository.productImage.ProductImageRepository;
import com.congthanh.productservice.service.*;
import com.congthanh.productservice.utils.Helper;
import com.congthanh.productservice.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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

    private final ProductCategoryService productCategoryService;

    private final ProductAttributeService productAttributeService;

    private final CommandGateway commandGateway;

    private final QueryGateway queryGateway;

    private final KafkaTemplate<String, ProductDTO> kafkaTemplate;

    private final CategoryGrpcClient categoryGrpcClient;

    private final AwsS3Service awsS3Service;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    private final ProductImageRepository productImageRepository;

    @Override
    @Cacheable("products")
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
    @Cacheable(cacheNames = "products", key = "product-#id")
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
    public ProductDTO getProductDTOBySlug(String slug) {
        ProductDocument data = queryGateway.query(new GetProductBySlugQuery(slug), ResponseTypes.instanceOf(ProductDocument.class)).join();
        return ProductDTO.builder()
                .id(data.getId())
                .name(data.getName())
                .slug(data.getSlug())
                .build();
    }

    @Override
    public ProductVm getProductVmBySlug(String slug) {
        ProductDocument data = queryGateway.query(new GetProductBySlugQuery(slug), ResponseTypes.instanceOf(ProductDocument.class)).join();
        return ProductVm.builder()
                .id(data.getId())
                .name(data.getName())
                .slug(data.getSlug())
                .description(data.getDescription())
                .build();
    }

    @Override
    public ProductDetailVm getProductDetailBySlug(String slug) {
        Product product = productRepository.findProductBySlug(slug).orElseThrow(() -> new NotFoundException("Product not found"));

        List<String> categoryIds = product.getCategory().stream().map(ProductCategory::getCategoryId).toList();
        List<String> category = categoryGrpcClient.getListCategoryByIds(categoryIds).stream().map(CategoryResponse::getName).toList();


//        List<String> images = productImageRepository.getProductImages(product.getId()).stream().map(ProductImage::getImagePath).toList();
        List<String> images = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(product.getImage())) {
            for (ProductImage image : product.getImage()) {
                images.add(image.getImagePath());
            }
        }

        Set<ProductAttributeValue> productAttributeValues = product.getAttribute();
        System.out.println("RRRRRRRRRRRRRRRRRRR"+ productAttributeValues);
        List<ProductAttributeVm> attributeVms = new ArrayList<>();
//        if (CollectionUtils.isNotEmpty(productAttributeValues)) {
//            List<ProductAttributeGroup> productAttributeGroups = productAttributeValues.stream()
//                    .map(productAttributeValue
//                            -> productAttributeValue.getProductAttribute().getProductAttributeGroup())
//                    .distinct()
//                    .toList();

        return new ProductDetailVm(
                product.getId(),
                product.getName(),
                product.getBrand() == null ? null : product.getBrand().toString(),
                category,
                attributeVms,
                product.getDescription(),
                product.isFeatured(),
                product.isHasVariant(),
                product.getPrice(),
                product.getThumbnail(),
                images
        );
    }

    @Override
    public ProductDTO createProduct(CreateProductRequest request) {

        categoryGrpcClient.validateCategory(request.getCategory());

        String thumbnailUrl = awsS3Service.uploadFile(request.getThumbnail());

        List<ProductImageRequest> images = request.getImage().stream().map(image -> {
            String url = awsS3Service.uploadFile(image);
            return ProductImageRequest.builder()
                    .id(snowflakeIdGenerator.nextId())
                    .imagePath(url)
                    .displayOrder(1)
                    .build();
        }).toList();
//        assert category != null && subcategory != null && supplier != null && brand != null;
        CreateProductCommand mainProduct = CreateProductCommand.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .category(request.getCategory())
                .description(request.getDescription())
                .thumbnail(thumbnailUrl)
                .image(images)
//                .brand((null)
                .status(ProductStatus.ACTIVE)
                .slug(Helper.generateSlug(request.getName()))
//                .supplier(supplier.getId())
                .build();
        ProductDTO result = commandGateway.sendAndWait(mainProduct);


//        kafkaTemplate.send("create-product-topic", result);
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

    @Override
    public ResponseWithPagination<ProductDTO> getProductByCategoryId(String categoryId, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Product> result = productRepository.findByCategory(categoryId, pageable);
        ResponseWithPagination<ProductDTO> response = new ResponseWithPagination<>();
        if (result.hasContent()) {
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
        } else {
            throw new RuntimeException("List empty exception");
        }
        return response;
    }

    @Override
    public ResponseWithPagination<ProductDTO> getProductByCategorySlug(String slug, int page, int limit) {
        CategoryResponse category = categoryGrpcClient.getCategoryBySlug(slug);
        Pageable pageable = PageRequest.of(page, limit);
        Page<Product> result = productRepository.findByCategory(category.getId(), pageable);
        ResponseWithPagination<ProductDTO> response = new ResponseWithPagination<>();
        if (result.hasContent()) {
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
        } else {
            throw new RuntimeException("List empty exception");
        }
        return response;
    }

    @Override
    public List<ProductDTO> searchProduct(String keyword) {
        List<Product> data = productRepository.searchProduct(keyword);
        System.out.println("CHECKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK" + data);

        if (!data.isEmpty()) {
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

//    @Override
//    public List<ProductVariantAttributeValueDTO> getVariantAttributeValueByProduct(String productId) {
//        List<Tuple> data = (List<Tuple>) productRepository.getVariantAttributeValueByProduct(productId);
//        if (!data.isEmpty()) {
//            Map<String, ProductVariantAttributeValueDTO> responseMap = new HashMap<>();
//            long idCounter = 1;
//
//            for (Tuple row : data) {
//                String attributeName = row.get("variantAttributeName", String.class);
//                String attributeValue = row.get("variantAttributeValue", String.class);
//
//                if (!responseMap.containsKey(attributeName)) {
//                    ProductVariantAttributeValueDTO response = new ProductVariantAttributeValueDTO();
//                    response.setId(idCounter++);
//                    response.setAttributeName(attributeName);
//                    response.setValue(new ArrayList<>());
//                    responseMap.put(attributeName, response);
//                }
//
//                ProductVariantAttributeValueDTO.Value value = new ProductVariantAttributeValueDTO.Value();
//                value.setId(Long.valueOf(row.get("variantAttributeId", Integer.class)));
//                value.setValue(attributeValue);
//
//                responseMap.get(attributeName).getValue().add(value);
//            }
//
//            return new ArrayList<>(responseMap.values());
//        }
//        return null;
//    }

}

