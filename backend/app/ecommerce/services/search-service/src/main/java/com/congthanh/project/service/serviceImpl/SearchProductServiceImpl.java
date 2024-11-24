package com.congthanh.project.service.serviceImpl;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.congthanh.project.constant.common.ProductField;
import com.congthanh.project.constant.enums.SortType;
import com.congthanh.project.model.document.Product;
import com.congthanh.project.model.request.QueryCriteria;
import com.congthanh.project.model.response.ProductGetVm;
import com.congthanh.project.model.response.ProductListGetVm;
import com.congthanh.project.model.response.ProductNameGetVm;
import com.congthanh.project.model.response.ProductNameListVm;
import com.congthanh.project.service.SearchProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchProductServiceImpl implements SearchProductService {

    private final ElasticsearchOperations elasticsearchOperations;

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
