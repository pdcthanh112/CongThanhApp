package com.congthanh.catalogservice.service.serviceImpl;

import com.congthanh.catalogservice.model.entity.Country;
import com.congthanh.catalogservice.repository.country.CountryRepository;
import com.congthanh.catalogservice.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

}
