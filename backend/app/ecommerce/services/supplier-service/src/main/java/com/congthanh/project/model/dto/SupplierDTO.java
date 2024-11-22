package com.congthanh.project.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull
    private String name;

    private String avatar;

    private String background;

    @NotNull
    private String slug;

}
