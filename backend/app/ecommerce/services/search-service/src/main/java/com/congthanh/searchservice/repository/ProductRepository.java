package com.congthanh.searchservice.repository;

import com.congthanh.searchservice.model.document.Product;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    List<Product> findByNameContainingIgnoreCase(String query);

    @Query("{\"bool\": {\"should\": [" +
            "{\"match\": {\"name\": \"?0\"}}," +
            "{\"match\": {\"description\": \"?0\"}}," +
            "{\"match\": {\"tags\": \"?0\"}}" +
            "]}}")
    List<Product> searchAcrossFields(String query);

}
