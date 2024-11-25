package com.congthanh.warehouseservice.config;//package com.congthanh.project.config;
//
//import java.sql.Connection;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManagerFactory;
//import org.axonframework.common.jdbc.ConnectionProvider;
//import org.axonframework.common.jdbc.UnitOfWorkAwareConnectionProviderWrapper;
//import org.axonframework.common.jpa.EntityManagerProvider;
//import org.axonframework.eventhandling.tokenstore.TokenStore;
//import org.axonframework.eventhandling.tokenstore.jdbc.JdbcTokenStore;
//import org.axonframework.eventhandling.tokenstore.jdbc.TokenTableFactory;
//import org.axonframework.eventhandling.tokenstore.jpa.JpaTokenStore;
//import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
//import org.axonframework.eventsourcing.eventstore.jdbc.EventTableFactory;
//import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
//import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
//import org.axonframework.modelling.saga.repository.SagaStore;
//import org.axonframework.modelling.saga.repository.jpa.JpaSagaStore;
//import org.axonframework.serialization.Serializer;
//import org.axonframework.serialization.xml.XStreamSerializer;
//import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
//import org.axonframework.springboot.util.RegisterDefaultEntities;
//import org.axonframework.springboot.util.jpa.ContainerManagedEntityManagerProvider;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableTransactionManagement
//@RegisterDefaultEntities(
//        packages = {
//                "org.axonframework.eventsourcing.eventstore.jpa"
//        }
//)
//public class AxonConfig {
//
////    @Bean(name = "axonDataSource")
////    @ConfigurationProperties(prefix = "axon.datasource")
////    public DataSource axonDataSource() {
////        return DataSourceBuilder.create().build();
////    }
////
////    @Bean(name = "axonEntityManagerFactory")
////    public LocalContainerEntityManagerFactoryBean axonEntityManagerFactory(
////            @Qualifier("axonDataSource") DataSource dataSource,
////            @Value("${spring.profiles.active:}") String activeProfile) {
////
////        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
////        em.setDataSource(dataSource);
////        em.setPackagesToScan(
////                "org.axonframework.eventsourcing.eventstore.jpa",
////                "org.axonframework.modelling.saga.repository.jpa",
////                "org.axonframework.eventhandling.tokenstore.jpa"
////        );
////
////        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
////        vendorAdapter.setGenerateDdl(true);
////        em.setJpaVendorAdapter(vendorAdapter);
////
////        Map<String, Object> properties = new HashMap<>();
////        properties.put("hibernate.hbm2ddl.auto", "update");
////
////        if (activeProfile.contains("dev") || activeProfile.contains("test")) {
////            properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
////        } else {
////            properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
////        }
////
////        em.setJpaPropertyMap(properties);
////        return em;
////    }
////
////    @Bean(name = "axonTransactionManager")
////    public PlatformTransactionManager axonTransactionManager(
////            EntityManagerFactory axonEntityManagerFactory) {
////        return new JpaTransactionManager(axonEntityManagerFactory);
////    }
////
////    @Bean
////    public EntityManagerProvider axonEntityManagerProvider(
////            @Qualifier("axonEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
////        return new ContainerManagedEntityManagerProvider();
////    }
////
////    @Bean
////    public EventStorageEngine eventStorageEngine(
////            EntityManagerProvider axonEntityManagerProvider,
////            PlatformTransactionManager axonTransactionManager) {
//////            @Qualifier("axonTransactionManager") PlatformTransactionManager axonTransactionManager) {
////        return JpaEventStorageEngine.builder()
////                .entityManagerProvider(axonEntityManagerProvider)
////                .transactionManager(new SpringTransactionManager(axonTransactionManager))
////                .build();
////    }
////
////    @Bean
////    public TokenStore tokenStore(EntityManagerProvider axonEntityManagerProvider) {
////        return JpaTokenStore.builder()
////                .entityManagerProvider(axonEntityManagerProvider)
////                .serializer(XStreamSerializer.builder().build())
////                .build();
////    }
////
////    @Bean
////    public SagaStore sagaStore(EntityManagerProvider axonEntityManagerProvider) {
////        return JpaSagaStore.builder()
////                .entityManagerProvider(axonEntityManagerProvider)
////                .serializer(XStreamSerializer.builder().build())
////                .build();
////    }
//
//    @Value("${spring.profiles.active:dev}")
//    private String activeProfile;
//
//    @Bean
//    @Qualifier("axonTransactionManager")
//    public PlatformTransactionManager axonTransactionManager(
//            @Qualifier("axonDataSource") DataSource axonDataSource) {
//        return new DataSourceTransactionManager(axonDataSource);
//    }
//
//    @Bean
//    public TransactionManager axonAxonTransactionManager(
//            @Qualifier("axonTransactionManager") PlatformTransactionManager transactionManager) {
//        return (TransactionManager) new SpringTransactionManager(transactionManager);
//    }
//
//    @Bean
//    public TokenStore tokenStore(Serializer serializer,
//                                 @Qualifier("axonDataSource") DataSource dataSource) {
//        return JdbcTokenStore.builder()
//                .connectionProvider(new UnitOfWorkAwareConnectionProviderWrapper(
//                        dataSource::getConnection
//                ))
//                .serializer(serializer)
//                .build();
//    }
//
//    @Bean
//    public EventStorageEngine eventStorageEngine(Serializer serializer,
//                                                 @Qualifier("axonDataSource") DataSource dataSource,
//                                                 org.axonframework.common.transaction.TransactionManager transactionManager) {
//        return JdbcEventStorageEngine.builder()
//                .connectionProvider(new UnitOfWorkAwareConnectionProviderWrapper(
//                        new ConnectionProvider() {
//                            @Override
//                            public Connection getConnection() throws SQLException {
//                                return dataSource.getConnection();
//                            }
//                        }
//                ))
//                .transactionManager(transactionManager)
//                .eventSerializer(serializer)
//                .snapshotSerializer(serializer)
//                .build();
//    }
//
//    @Bean
//    @Qualifier("axonDataSource")
//    @ConfigurationProperties(prefix = "axon.datasource")
//    public DataSource axonDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    public EntityManagerProvider entityManagerProvider() {
//        return new ContainerManagedEntityManagerProvider();
//    }
//
//}
