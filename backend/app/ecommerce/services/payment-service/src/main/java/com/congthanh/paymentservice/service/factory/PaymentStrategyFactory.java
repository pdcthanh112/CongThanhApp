package com.congthanh.paymentservice.service.factory;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
import com.congthanh.paymentservice.exception.ecommerce.UnsupportedPaymentMethodException;
import com.congthanh.paymentservice.service.strategy.PaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PaymentStrategyFactory implements PaymentProviderFactory{

    private final Map<PaymentMethod, PaymentStrategy> strategies;

    @Autowired
    public PaymentStrategyFactory(List<PaymentStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        PaymentStrategy::paymentMethod,
                        strategy -> strategy
                ));
    }

    @Override
    public PaymentStrategy createPaymentStrategy(PaymentMethod method) {
        return Optional.ofNullable(strategies.get(method))
                .orElseThrow(() -> new UnsupportedPaymentMethodException("Unsupported Payment Method: " + method));
    }
}
