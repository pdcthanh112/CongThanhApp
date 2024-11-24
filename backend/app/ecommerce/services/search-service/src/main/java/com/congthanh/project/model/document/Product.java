package com.congthanh.project.model.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(indexName = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setting(settingPath = "esconfig/elastic-analyzer.json")
public class Product {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "autocomplete_index", searchAnalyzer = "autocomplete_search")
    private String name;

    private String slug;

    @Field(type = FieldType.Double)
    private BigDecimal price;

    private Boolean isPublished;

    private Boolean isVisibleIndividually;

    private Boolean isAllowedToOrder;

    private Boolean isFeatured;

    private Long thumbnailMediaId;

    @Field(type = FieldType.Text, fielddata = true)
    private String brand;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private List<String> attributes;

    @Field(type = FieldType.Date)
    private Instant createdOn;

}
