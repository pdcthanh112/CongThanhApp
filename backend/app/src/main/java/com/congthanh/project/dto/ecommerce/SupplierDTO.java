package com.congthanh.project.dto.ecommerce;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierDTO {

    private String id;

    @NotNull
    private String name;

    private String avatar;

    private String background;

    @NotNull
    private String domain;

}
