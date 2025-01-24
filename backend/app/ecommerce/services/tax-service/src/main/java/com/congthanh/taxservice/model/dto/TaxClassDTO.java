package com.congthanh.taxservice.model.dto;

import com.congthanh.taxservice.model.entity.TaxClass;

public record TaxClassDTO(Long id, String name) {

    public static TaxClassDTO fromModel(TaxClass taxClass) {
        return new TaxClassDTO(taxClass.getId(), taxClass.getName());
    }
}
