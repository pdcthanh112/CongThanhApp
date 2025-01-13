package com.congthanh.catalogservice.controller;

import com.congthanh.catalogservice.constant.common.ResponseStatus;
import com.congthanh.catalogservice.model.entity.Country;
import com.congthanh.catalogservice.model.request.RequestFilter;
import com.congthanh.catalogservice.model.response.Response;
import com.congthanh.catalogservice.repository.country.CountryRepository;
import com.congthanh.catalogservice.service.CountryService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryRepository countryRepository;

    private final CountryService countryService;

    @GetMapping("/")
    @PermitAll
    public ResponseEntity<?> getAll() {
        List<Country> countries = countryRepository.findAll();
        return ResponseEntity.ok().body(countries);
    }

    @QueryMapping(value = "countries")
    List<Country> countries() {
        return countryRepository.findAll();
    };
}
