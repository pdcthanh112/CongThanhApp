package com.congthanh.orderservice.config;

import com.congthanh.orderservice.cqrs.command.aggregate.OrderAggregate;
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
    public EventSourcingRepository<OrderAggregate> orderAggregateRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(OrderAggregate.class).eventStore(eventStore).build();
    }

}
