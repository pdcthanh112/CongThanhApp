package com.congthanh.taxservice.service.serviceImpl;

import com.congthanh.taxservice.exception.ecommerce.NotFoundException;
import com.congthanh.taxservice.model.entity.TaxRate;
import com.congthanh.taxservice.repository.taxClass.TaxClassRepository;
import com.congthanh.taxservice.repository.taxRate.TaxRateRepository;
import com.congthanh.taxservice.service.TaxRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaxRateServiceImpl implements TaxRateService {

    private final TaxRateRepository taxRateRepository;

    private final TaxClassRepository taxClassRepository;

    @Transactional
    public TaxRate createTaxRate(final TaxRatePostVm taxRatePostVm) {

        final Long taxClassId = taxRatePostVm.taxClassId();
        final boolean isTaxClassExisted = taxClassRepository.existsById(taxClassId);
        if (!isTaxClassExisted) {
            throw new NotFoundException(MessageCode.TAX_CLASS_NOT_FOUND, taxClassId);
        }

        final TaxRate taxRate = TaxRate.builder()
                .rate(taxRatePostVm.rate())
                .zipCode(taxRatePostVm.zipCode())
                .taxClass(taxClassRepository.getReferenceById(taxClassId))
                .stateOrProvinceId(taxRatePostVm.stateOrProvinceId())
                .countryId(taxRatePostVm.countryId())
                .build();

        return taxRateRepository.save(taxRate);
    }

    /**
     * Handle business and update state or province.
     *
     * @param taxRatePostVm The state or province post Dto
     * @param id            The id of TaxRate need to update
     */
    @Transactional
    public void updateTaxRate(final TaxRatePostVm taxRatePostVm,
                              final Long id) {
        final TaxRate taxRate = taxRateRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException(MessageCode.TAX_RATE_NOT_FOUND, id));

        final Long taxClassId = taxRatePostVm.taxClassId();
        final boolean isTaxClassExisted = taxClassRepository.existsById(taxClassId);
        if (!isTaxClassExisted) {
            throw new NotFoundException(MessageCode.TAX_CLASS_NOT_FOUND, taxClassId);
        }
        taxRate.setRate(taxRatePostVm.rate());
        taxRate.setZipCode(taxRatePostVm.zipCode());
        taxRate.setTaxClass(taxClassRepository.getReferenceById(taxClassId));
        taxRate.setStateOrProvinceId(taxRatePostVm.stateOrProvinceId());
        taxRate.setCountryId(taxRatePostVm.countryId());

        taxRateRepository.save(taxRate);
    }

    @Transactional
    public void delete(final Long id) {
        final boolean isTaxRateExisted = taxRateRepository.existsById(id);
        if (!isTaxRateExisted) {
            throw new NotFoundException(MessageCode.TAX_RATE_NOT_FOUND, id);
        }
        taxRateRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TaxRateVm findById(final Long id) {
        final TaxRate taxRate = taxRateRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageCode.TAX_RATE_NOT_FOUND, id));
        return TaxRateVm.fromModel(taxRate);
    }

    @Transactional(readOnly = true)
    public List<TaxRateVm> findAll() {
        return taxRateRepository
                .findAll()
                .stream()
                .map(TaxRateVm::fromModel)
                .toList();
    }

    /**
     * Handle business and paging list of tax rates.
     *
     * @param pageNo   The number of page
     * @param pageSize The number of row on every page
     * @return TaxRateListGetVm
     */
    @Transactional(readOnly = true)
    public TaxRateListGetVm getPageableTaxRates(int pageNo, int pageSize) {
        final Pageable pageable = PageRequest.of(pageNo, pageSize);
        final Page<TaxRate> taxRatePage = taxRateRepository.findAll(pageable);
        final List<TaxRate> taxRates = taxRatePage.getContent();
        final List<TaxRateGetDetailVm> taxRateGetDetailVms = new ArrayList<>();

        //Filter list of state or province ids
        final List<Long> stateOrProvinceIds =
                taxRates.stream().distinct().map(el -> el.getStateOrProvinceId()).collect(Collectors.toList());

        if (!stateOrProvinceIds.isEmpty()) {
            //Call location service to get country names and state or province name by list of state or province ids
            List<StateOrProvinceAndCountryGetNameVm> stateOrProvinceAndCountryGetNameVms =
                    locationService.getStateOrProvinceAndCountryNames(stateOrProvinceIds);
            taxRates.forEach(taxRate -> {
                stateOrProvinceAndCountryGetNameVms.stream()
                        .filter(x -> x.stateOrProvinceId().equals(taxRate.getStateOrProvinceId()))
                        .findAny()
                        .ifPresent(stateOrProvinceAndCountryGetNameVm -> taxRateGetDetailVms.add(new TaxRateGetDetailVm(
                                taxRate.getId(),
                                taxRate.getRate(),
                                taxRate.getZipCode(),
                                taxRate.getTaxClass().getName(),
                                stateOrProvinceAndCountryGetNameVm.stateOrProvinceName(),
                                stateOrProvinceAndCountryGetNameVm.countryName())));

            });
        }

        return new TaxRateListGetVm(
                taxRateGetDetailVms,
                taxRatePage.getNumber(),
                taxRatePage.getSize(),
                (int) taxRatePage.getTotalElements(),
                taxRatePage.getTotalPages(),
                taxRatePage.isLast()
        );
    }

    public double getTaxPercent(Long taxClassId, Long countryId, Long stateOrProvinceId, String zipCode) {
        Double taxPercent = taxRateRepository.getTaxPercent(countryId, stateOrProvinceId, zipCode, taxClassId);
        if (taxPercent != null) {
            return taxPercent;
        }

        return 0;
    }

    public List<TaxRateVm> getBulkTaxRate(List<Long> taxClassIds,
                                          Long countryId,
                                          Long stateOrProvinceId,
                                          String zipCode) {
        return taxRateRepository.getBatchTaxRates(countryId,
                        stateOrProvinceId,
                        zipCode,
                        new HashSet<>(taxClassIds))
                .stream().map(TaxRateVm::fromModel).toList();
    }
}
