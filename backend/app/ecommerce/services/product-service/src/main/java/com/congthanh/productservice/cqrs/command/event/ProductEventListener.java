package com.congthanh.productservice.cqrs.command.event;

import com.congthanh.productservice.model.document.ProductDocument;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventListener {

//    private final ProductDocumentRepository productDocumentRepository;

    private final ObjectMapper objectMapper;

    public void handleProductCreated(ProductDocument productDocument) {

    }


}
