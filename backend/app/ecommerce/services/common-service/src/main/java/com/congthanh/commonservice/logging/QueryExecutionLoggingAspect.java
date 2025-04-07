package com.congthanh.commonservice.logging;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.congthanh.commonservice.constants.Constants.Logging.DURATION;
import static com.congthanh.commonservice.constants.Constants.Logging.QUERY_METHOD;

@Aspect
@Component
@Slf4j
public class QueryExecutionLoggingAspect {

    //bọc các repository con của JpaRepository
    @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    public Object logQueryExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;
        //ThreadContext sẽ gắn thêm thuộc tính cho log event
        ThreadContext.put(QUERY_METHOD, joinPoint.getSignature().toShortString());
        ThreadContext.put(DURATION, String.valueOf(duration));
        log.info("Query Method: {} executed in {} ms", joinPoint.getSignature().toShortString(), duration);
        ThreadContext.remove(QUERY_METHOD);
        ThreadContext.remove(DURATION);
        return result;
    }
}
