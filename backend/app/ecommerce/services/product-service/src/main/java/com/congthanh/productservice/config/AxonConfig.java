package com.congthanh.productservice.config;

import com.congthanh.productservice.cqrs.command.aggregate.ProductAggregate;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig{



}
