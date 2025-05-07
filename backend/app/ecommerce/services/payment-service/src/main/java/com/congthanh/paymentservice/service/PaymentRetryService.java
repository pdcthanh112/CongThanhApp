package com.congthanh.paymentservice.service;

import com.congthanh.paymentservice.exception.ecommerce.PaymentGatewayTemporaryErrorException;
import com.congthanh.paymentservice.exception.ecommerce.PaymentRetryExhaustedException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.NetworkException;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Callable;

@Component
@RequiredArgsConstructor
public class PaymentRetryService {

    private static final int MAX_RETRIES = 3;

    private final RetryTemplate retryTemplate;

    public <T> T executeWithRetry(Callable<T> paymentTask) throws Exception {
        return retryTemplate.execute(
                context -> paymentTask.call(),
                context -> {
                    // Xử lý khi retry thất bại
                    throw new PaymentRetryExhaustedException(
                            "Payment failed after " + MAX_RETRIES + " attempts"
                    );
                }
        );
    }

    @Bean
    public RetryTemplate createRetryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(createRetryPolicy());
        retryTemplate.setBackOffPolicy(createExponentialBackoffPolicy());
        return retryTemplate;
    }

    private RetryPolicy createRetryPolicy() {
        return new SimpleRetryPolicy(MAX_RETRIES, Map.of(
                NetworkException.class, true,
                PaymentGatewayTemporaryErrorException.class, true
        )
        );
    }

    private BackOffPolicy createExponentialBackoffPolicy() {
        return new ExponentialBackOffPolicy() {{
            setInitialInterval(1000);
            setMultiplier(2.0);
            setMaxInterval(30000);
        }};
    }

}
