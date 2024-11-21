package com.congthanh.project.service.factory;

import com.congthanh.project.constant.enums.PaymentMethod;
import com.congthanh.project.service.strategy.PaymentStrategy;

public interface PaymentProviderFactory {

    PaymentStrategy createPaymentStrategy(PaymentMethod method);

}
