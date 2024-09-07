package com.congthanh.project.cqrs.command.aggregate;

import com.congthanh.project.constant.enums.ProductStatus;
import com.congthanh.project.cqrs.command.command.CreateProductCommand;
import com.congthanh.project.cqrs.command.event.ProductCreatedEvent;
import com.congthanh.project.dto.ProductAttributeValueDTO;
import com.congthanh.project.dto.ProductImageDTO;
import com.congthanh.project.dto.ProductVariantDTO;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class ProductAggregate {

    @AggregateIdentifier
    private String id;
    private String name;
    private String category;
    private String subcategory;
    private String slug;
    private String description;
    private List<ProductImageDTO> image;
    private List<ProductAttributeValueDTO> attribute;
    private String supplier;
    private String brand;
    private List<ProductVariantDTO> variant;
    private ProductStatus status;

    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        apply(new ProductCreatedEvent(command.getId(), command.getName(), command.getCategory(), command.getSubcategory(), command.getSlug(), command.getDescription(), command.getImage(), command.getAttribute(), command.getSupplier(), command.getBrand(), command.getVariant(), command.getStatus()));
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.category = event.getCategory();
        this.subcategory = event.getSubcategory();
        this.slug = event.getSlug();
        this.description = event.getDescription();
        this.image = event.getImage();
        this.attribute = event.getAttribute();
        this.supplier = event.getSupplier();
        this.brand = event.getBrand();
        this.variant = event.getVariant();
        this.status = event.getStatus();
    }

}
