package com.congthanh.project.cqrs.command.event;

import com.congthanh.project.model.document.ProductDocument;
import com.congthanh.project.repository.product.ProductDocumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventListener {

    private final ProductDocumentRepository productDocumentRepository;

    private final ObjectMapper objectMapper;

    public void handleProductCreated(ProductDocument productDocument) {

    }


}
