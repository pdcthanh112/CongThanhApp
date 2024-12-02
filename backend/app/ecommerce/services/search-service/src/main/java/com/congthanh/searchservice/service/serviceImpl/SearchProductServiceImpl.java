package com.congthanh.searchservice.service.serviceImpl;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.congthanh.searchservice.constant.common.ProductField;
import com.congthanh.searchservice.constant.enums.SortType;
import com.congthanh.searchservice.model.document.Product;
import com.congthanh.searchservice.model.request.QueryCriteria;
import com.congthanh.searchservice.model.response.ProductGetVm;
import com.congthanh.searchservice.model.response.ProductListGetVm;
import com.congthanh.searchservice.model.response.ProductNameGetVm;
import com.congthanh.searchservice.model.response.ProductNameListVm;
import com.congthanh.searchservice.repository.ProductRepository;
import com.congthanh.searchservice.service.SearchProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchProductServiceImpl implements SearchProductService {

    private final ProductRepository productRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_PREFIX = "product_search:";

    @Cacheable(value = "productSearchCache", key = "#query")
    public List<Product> searchProducts(String query) {
        String cacheKey = CACHE_PREFIX + query;

        // Kiểm tra cache Redis
        List<Product> cachedResults = (List<Product>) redisTemplate.opsForValue().get(cacheKey);

        if (cachedResults != null) {
            return cachedResults;
        }

        // Tìm kiếm trên Elasticsearch
        List<Product> results = productRepository.searchAcrossFields(query);

        // Lưu vào cache Redis
        redisTemplate.opsForValue().set(
                cacheKey,
                results,
                Duration.ofHours(1)
        );

        return results;
    }

    @Override
    public ProductListGetVm findProductAdvance(QueryCriteria productCriteria) {
        NativeQueryBuilder nativeQuery = NativeQuery.builder()
                .withAggregation("categories", Aggregation.of(a -> a
                        .terms(ta -> ta.field(ProductField.CATEGORIES))))
                .withAggregation("attributes", Aggregation.of(a -> a
                        .terms(ta -> ta.field(ProductField.ATTRIBUTES))))
                .withAggregation("brands", Aggregation.of(a -> a
                        .terms(ta -> ta.field(ProductField.BRAND))))
                .withQuery(q -> q
                        .bool(b -> b
                                .should(s -> s
                                        .multiMatch(m -> m
                                                .fields(ProductField.NAME, ProductField.BRAND, ProductField.CATEGORIES)
                                                .query(productCriteria.keyword())
                                                .fuzziness(Fuzziness.ONE.asString())
                                        )
                                )
                        )
                )
                .withPageable(PageRequest.of(productCriteria.page(), productCriteria.size()));


        nativeQuery.withFilter(f -> f
                .bool(b -> {
                    extractedTermsFilter(productCriteria.brand(), ProductField.BRAND, b);
                    extractedTermsFilter(productCriteria.category(), ProductField.CATEGORIES, b);
                    extractedTermsFilter(productCriteria.attribute(), ProductField.ATTRIBUTES, b);
                    extractedRange(productCriteria.minPrice(), productCriteria.maxPrice(), b);
                    b.must(m -> m.term(t -> t.field(ProductField.IS_PUBLISHED).value(true)));
                    return b;
                })
        );

        if (productCriteria.sortType() == SortType.PRICE_ASC) {
            nativeQuery.withSort(Sort.by(Sort.Direction.ASC, ProductField.PRICE));
        } else if (productCriteria.sortType() == SortType.PRICE_DESC) {
            nativeQuery.withSort(Sort.by(Sort.Direction.DESC, ProductField.PRICE));
        } else {
            nativeQuery.withSort(Sort.by(Sort.Direction.DESC, ProductField.CREATE_ON));
        }

        SearchHits<Product> searchHitsResult = elasticsearchOperations.search(nativeQuery.build(), Product.class);
        SearchPage<Product> productPage = SearchHitSupport.searchPageFor(searchHitsResult, nativeQuery.getPageable());

        List<ProductGetVm> productListVmList = searchHitsResult.stream()
                .map(i -> ProductGetVm.fromModel(i.getContent())).toList();

        return new ProductListGetVm(
                productListVmList,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast(),
                getAggregations(searchHitsResult));
    }

    private void extractedTermsFilter(String fieldValues, String productField, BoolQuery.Builder b) {
        if (StringUtils.isBlank(fieldValues)) {
            return;
        }
        String[] valuesArray = fieldValues.split(",");
        b.must(m -> {
            BoolQuery.Builder innerBool = new BoolQuery.Builder();
            for (String value : valuesArray) {
                innerBool.should(s -> s
                        .term(t -> t
                                .field(productField)
                                .value(value)
                                .caseInsensitive(true)
                        )
                );
            }
            return new Query.Builder().bool(innerBool.build());
        });
    }

    private void extractedRange(Number min, Number max, BoolQuery.Builder bool) {
        if (min != null || max != null) {
            bool.must(m -> m
                    .range(r -> r
                            .field(ProductField.PRICE)
                            .from(min != null ? min.toString() : null)
                            .to(max != null ? max.toString() : null)
                    )
            );
        }
    }

    private Map<String, Map<String, Long>> getAggregations(SearchHits<Product> searchHits) {
        List<org.springframework.data.elasticsearch.client.elc.Aggregation> aggregations = new ArrayList<>();
        if (searchHits.hasAggregations()) {
            ((List<ElasticsearchAggregation>) searchHits.getAggregations().aggregations())
                    .forEach(elsAgg -> aggregations.add(elsAgg.aggregation()));
        }

        Map<String, Map<String, Long>> aggregationsMap = new HashMap<>();
        aggregations.forEach(agg -> {
            Map<String, Long> aggregation = new HashMap<>();
            StringTermsAggregate stringTermsAggregate = (StringTermsAggregate) agg.getAggregate()._get();
            List<StringTermsBucket> stringTermsBuckets
                    = (List<StringTermsBucket>) stringTermsAggregate.buckets()._get();
            stringTermsBuckets.forEach(bucket -> aggregation.put(bucket.key()._get().toString(), bucket.docCount()));
            aggregationsMap.put(agg.getName(), aggregation);
        });

        return aggregationsMap;
    }

    @Override
    public ProductNameListVm autoCompleteProductName(final String keyword) {
        NativeQuery matchQuery = NativeQuery.builder()
                .withQuery(
                        q -> q.matchPhrasePrefix(
                                matchPhrasePrefix -> matchPhrasePrefix.field("name").query(keyword)
                        )
                )
                .withSourceFilter(new FetchSourceFilter(
                        new String[]{"name"},
                        null)
                )
                .build();
        SearchHits<Product> result = elasticsearchOperations.search(matchQuery, Product.class);
        List<Product> products = result.stream().map(SearchHit::getContent).toList();

        return new ProductNameListVm(products.stream().map(ProductNameGetVm::fromModel).toList());
    }

}
