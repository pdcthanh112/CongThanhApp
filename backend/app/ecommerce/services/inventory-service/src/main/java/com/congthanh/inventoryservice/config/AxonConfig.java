package com.congthanh.inventoryservice.config;

import com.congthanh.inventoryservice.cqrs.command.aggregate.InventoryAggregate;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    public AxonConfig(EventProcessingConfigurer configurer) {
//        configurer.registerSaga(SagaConfigurer.defaultConfiguration());
    }

    @Bean
    public EventSourcingRepository<InventoryAggregate> inventoryAggregateRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(InventoryAggregate.class).eventStore(eventStore).build();
    }

}
