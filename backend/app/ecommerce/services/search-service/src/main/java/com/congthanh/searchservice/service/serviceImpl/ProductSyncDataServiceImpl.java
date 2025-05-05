package com.congthanh.searchservice.service.serviceImpl;

import com.congthanh.searchservice.repository.ProductRepository;
import com.congthanh.searchservice.service.ProductSyncDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSyncDataServiceImpl implements ProductSyncDataService {

    private final RestClient restClient;
    private final ServiceUrlConfig serviceUrlConfig;
    private final ProductRepository productRepository;

    @Override
    public ProductEsDetailVm getProductEsDetailById(String id) {
        final URI url = UriComponentsBuilder.fromHttpUrl(
                serviceUrlConfig.product()).path("/storefront/products-es/{id}").buildAndExpand(id).toUri();
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(ProductEsDetailVm.class);
    }

    @Override
    public void updateProduct(String id) {
        ProductEsDetailVm productEsDetailVm = getProductEsDetailById(id);
        Product product = productRepository.findById(id).orElseThrow(()
                -> new NotFoundException(MessageCode.PRODUCT_NOT_FOUND, id));

        if (!productEsDetailVm.isPublished()) {
            productRepository.deleteById(id);
            return;
        }

        product.setName(productEsDetailVm.name());
        product.setSlug(productEsDetailVm.slug());
        product.setPrice(productEsDetailVm.price());
        product.setIsPublished(true);
        product.setIsVisibleIndividually(productEsDetailVm.isVisibleIndividually());
        product.setIsAllowedToOrder(productEsDetailVm.isAllowedToOrder());
        product.setIsFeatured(productEsDetailVm.isFeatured());
        product.setThumbnailMediaId(productEsDetailVm.thumbnailMediaId());
        product.setBrand(productEsDetailVm.brand());
        product.setCategories(productEsDetailVm.categories());
        product.setAttributes(productEsDetailVm.attributes());
        productRepository.save(product);
    }

    @Override
    public void createProduct(String id) {
        ProductEsDetailVm productEsDetailVm = getProductEsDetailById(id);

        Product product = Product.builder()
                .id(id)
                .name(productEsDetailVm.name())
                .slug(productEsDetailVm.slug())
                .price(productEsDetailVm.price())
                .isPublished(productEsDetailVm.isPublished())
                .isVisibleIndividually(productEsDetailVm.isVisibleIndividually())
                .isAllowedToOrder(productEsDetailVm.isAllowedToOrder())
                .isFeatured(productEsDetailVm.isFeatured())
                .thumbnailMediaId(productEsDetailVm.thumbnailMediaId())
                .brand(productEsDetailVm.brand())
                .categories(productEsDetailVm.categories())
                .attributes(productEsDetailVm.attributes())
                .build();

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(String id) {
        final boolean isProductExisted = productRepository.existsById(id);
        if (isProductExisted) {
            productRepository.deleteById(id);
        } else {
            log.warn("Product {} doesn't exist in Elasticsearch.", id);
        }
    }

}
