package com.congthanh.project.config;

import com.congthanh.project.cqrs.command.aggregate.CartAggregate;
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
    public EventSourcingRepository<CartAggregate> productAggregateRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(CartAggregate.class).eventStore(eventStore).build();
    }

}
