package com.congthanh.messageservice.model.document;

import com.congthanh.catalogservice.constant.enums.TagStatus;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDocument {

    @Id
    private Long id;

    private String name;

    private Instant createdAt;

    private Instant updatedAt;

    private TagStatus status;

}
