package com.congthanh.productservice.cqrs.command.aggregate;

import com.congthanh.productservice.constant.enums.ProductStatus;
import com.congthanh.productservice.cqrs.command.command.CreateProductCommand;
import com.congthanh.productservice.cqrs.command.command.UpdateProductCommand;
import com.congthanh.productservice.cqrs.command.event.ProductCreatedEvent;
import com.congthanh.productservice.cqrs.command.event.ProductUpdatedEvent;
import com.congthanh.productservice.model.dto.attribute.ProductAttributeValueDTO;
import com.congthanh.productservice.model.dto.ProductImageDTO;
import com.congthanh.productservice.model.dto.variant.ProductVariantDTO;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String id;
    private String name;
    private String category;
    private String slug;
    private String description;
    private String thumbnail;
    private List<ProductImageDTO> image;
    private List<ProductAttributeValueDTO> attribute;
    private String supplier;
    private String brand;
    private List<Long> tag;
    private List<ProductVariantDTO> variant;
    private ProductStatus status;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
//        apply(new ProductCreatedEvent(command.getId(), command.getName(), command.getCategory(), command.getSlug(), command.getDescription(), command.getImage(), command.getAttribute(), command.getSupplier(), command.getBrand(), command.getVariant(), command.getStatus()));
        apply(ProductCreatedEvent.builder()
                .id(command.getId())
                .name(command.getName())
                .category(command.getCategory())
                .slug(command.getSlug())
                .description(command.getDescription())
                .thumbnail(command.getThumbnail())
                .status(command.getStatus())
                .build());
    }

    @EventSourcingHandler
    public void onProductCreated(ProductCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
//        this.category = event.getCategory();
        this.slug = event.getSlug();
        this.description = event.getDescription();
        this.thumbnail = event.getThumbnail();
        this.attribute = event.getAttribute();
        this.supplier = event.getSupplier();
        this.brand = event.getBrand();
        this.variant = event.getVariant();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handleUpdateProduct(UpdateProductCommand command) {
        apply(new ProductUpdatedEvent(command.getId(), command.getName(), command.getCategory(), command.getSlug(), command.getDescription(), command.getSupplier(), command.getBrand(),command.getImage(), command.getAttribute(), command.getVariant(), command.getStatus()));
    }

    @EventSourcingHandler
    public void onProductUpdated(ProductUpdatedEvent event) {
        this.id = event.getId();
    }

}
