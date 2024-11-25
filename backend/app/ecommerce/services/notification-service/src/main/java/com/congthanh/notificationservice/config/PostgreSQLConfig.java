package com.congthanh.notificationservice.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//
//@Configuration
//@EnableTransactionManagement
public class PostgreSQLConfig {
//
//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix = "spring.postgresql.datasource.ecommerce")
//    public DataSource ecommerceDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.postgresql.datasource.management")
//    public DataSource managementDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.postgresql.datasource.company")
//    public DataSource companyDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Primary
//    @Bean
//    public LocalContainerEntityManagerFactoryBean ecommerceEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("spring.jpa.hibernate.ddl-auto=update", "update");
//        return builder
//                .dataSource(ecommerceDataSource())
//                .properties(properties)
//                .packages("com.congthanh.project.entity.ecommerce")
//                .persistenceUnit("postgresql")
//                .build();
//    }
//
//    @Primary
//    @Bean
//    public PlatformTransactionManager postgresqlTransactionManager(
//            final @Qualifier("ecommerceEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory.getObject());
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean managementEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("spring.jpa.hibernate.ddl-auto=update", "update");
//        return builder
//                .dataSource(managementDataSource())
//                .properties(properties)
//                .packages("com.congthanh.project.entity.management")
//                .persistenceUnit("management")
//                .build();
//    }
//
//    @Bean
//    public PlatformTransactionManager managementTransactionManager(
//            final @Qualifier("managementEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory.getObject());
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean companyEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("spring.jpa.hibernate.ddl-auto=update", "update");
//        return builder
//                .dataSource(companyDataSource())
//                .properties(properties)
//                .packages("com.congthanh.project.entity.company")
//                .persistenceUnit("company")
//                .build();
//    }
//
//    @Bean
//    public PlatformTransactionManager companyTransactionManager(
//            final @Qualifier("companyEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory.getObject());
//    }
//
//    @EnableJpaRepositories(
//            basePackages = "com.congthanh.project.repository.ecommerce",
//            entityManagerFactoryRef = "ecommerceEntityManagerFactory",
//            transactionManagerRef = "postgresqlTransactionManager"
//    )
//    public class EcommerceRepositoryConfig {
//    }
//
//    @EnableJpaRepositories(
//            basePackages = "com.congthanh.project.repository.management",
//            entityManagerFactoryRef = "managementEntityManagerFactory",
//            transactionManagerRef = "managementTransactionManager"
//    )
//    public class ManagementRepositoryConfig {
//    }
//
//    @EnableJpaRepositories(
//            basePackages = "com.congthanh.project.repository.company",
//            entityManagerFactoryRef = "companyEntityManagerFactory",
//            transactionManagerRef = "companyTransactionManager"
//    )
//    public class CompanyRepositoryConfig {
//    }
}