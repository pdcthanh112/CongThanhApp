package com.congthanh.messageservice.model.document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "brand")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandDocument {

    @Id
    private String id;

    @NotNull
    private String name;

    private String image;

}
