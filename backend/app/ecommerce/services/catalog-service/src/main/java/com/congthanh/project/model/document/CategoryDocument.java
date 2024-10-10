package com.congthanh.project.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDocument {



}
