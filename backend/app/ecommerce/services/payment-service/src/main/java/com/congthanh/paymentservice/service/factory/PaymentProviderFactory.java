package com.congthanh.paymentservice.service.factory;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
import com.congthanh.paymentservice.service.strategy.PaymentStrategy;

public interface PaymentProviderFactory {

    PaymentStrategy createPaymentStrategy(PaymentMethod method);

}
