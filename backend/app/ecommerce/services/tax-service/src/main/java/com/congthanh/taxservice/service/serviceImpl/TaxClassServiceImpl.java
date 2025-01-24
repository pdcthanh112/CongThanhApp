package com.congthanh.taxservice.service.serviceImpl;

import com.congthanh.taxservice.exception.ecommerce.NotFoundException;
import com.congthanh.taxservice.model.dto.TaxClassDTO;
import com.congthanh.taxservice.model.entity.TaxClass;
import com.congthanh.taxservice.repository.taxClass.TaxClassRepository;
import com.congthanh.taxservice.service.TaxClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxClassServiceImpl implements TaxClassService {

    private final TaxClassRepository taxClassRepository;

    @Transactional(readOnly = true)
    public List<TaxClassDTO> findAllTaxClasses() {
        return taxClassRepository
                .findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(TaxClassDTO::fromModel)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaxClassDTO findById(final Long id) {
        final TaxClass taxClass = taxClassRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException(MessageCode.TAX_CLASS_NOT_FOUND, id));
        return TaxClassDTO.fromModel(taxClass);
    }

    @Transactional
    public TaxClass create(final TaxClassPostVm taxClassPostVm) {
        if (taxClassRepository.existsByName(taxClassPostVm.name())) {
            throw new DuplicatedException(MessageCode.NAME_ALREADY_EXITED, taxClassPostVm.name());
        }
        return taxClassRepository.save(taxClassPostVm.toModel());
    }

    @Transactional
    public void update(final TaxClassPostVm taxClassPostVm, final Long id) {
        final TaxClass taxClass = taxClassRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageCode.TAX_CLASS_NOT_FOUND, id));

        //For the updating case we don't need to check for the taxClass being updated
        if (taxClassRepository.existsByNameNotUpdatingTaxClass(taxClassPostVm.name(), id)) {
            throw new DuplicatedException(MessageCode.NAME_ALREADY_EXITED, taxClassPostVm.name());
        }

        taxClass.setName(taxClassPostVm.name());
        taxClassRepository.save(taxClass);
    }

    @Transactional
    public void delete(final Long id) {
        final boolean isTaxClassExisted = taxClassRepository.existsById(id);
        if (!isTaxClassExisted) {
            throw new NotFoundException(MessageCode.TAX_CLASS_NOT_FOUND, id);
        }
        taxClassRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TaxClassListGetVm getPageableTaxClasses(final int pageNo, final int pageSize) {
        final Pageable pageable = PageRequest.of(pageNo, pageSize);
        final Page<TaxClass> taxClassPage = taxClassRepository.findAll(pageable);
        final List<TaxClass> taxClassList = taxClassPage.getContent();

        final List<TaxClassVm> taxClassVms = taxClassList.stream()
                .map(TaxClassVm::fromModel)
                .toList();

        return new TaxClassListGetVm(
                taxClassVms,
                taxClassPage.getNumber(),
                taxClassPage.getSize(),
                (int) taxClassPage.getTotalElements(),
                taxClassPage.getTotalPages(),
                taxClassPage.isLast()
        );
    }

}
