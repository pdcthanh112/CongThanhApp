package com.congthanh.commonservice.logging;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.stat.internal.StatisticsImpl;

@Slf4j
public class CustomStatisticsImpl extends StatisticsImpl {

    public CustomStatisticsImpl(
            @UnknownKeyFor @NonNull @Initialized SessionFactoryImplementor sessionFactory
    ) {
        super(sessionFactory);
    }
}
