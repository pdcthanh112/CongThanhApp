package com.congthanh.project.cqrs.query.projection;

import com.congthanh.project.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProjection {

    private final OrderRepository orderRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        orderRepository.put(event.getOrderId(), event);
    }

    @QueryHandler
    public Object handle(GetOrderQuery query) {
        return orderRepository.get(query.getOrderId());
    }

}
