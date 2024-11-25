package com.congthanh.paymentservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.congthanh.project.repository",
//        entityManagerFactoryRef = "mysqlEntityManagerFactory",
//        transactionManagerRef = "mysqlTransactionManager")
public class MySQLConfig {

//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix = "spring.mysql.datasource")
//    public DataSource mysqlDataSource() {
//        return DataSourceBuilder.create().build();
//    }

//    @Primary
//    @Bean
//    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("spring.jpa.hibernate.ddl-auto=update", "update");
//        return builder
//                .dataSource(mysqlDataSource())
//                .properties(properties)
//                .packages("com.congthanh.project.entity")
//                .persistenceUnit("mysql")
//                .build();
//    }

//    @Primary
//    @Bean
//    public PlatformTransactionManager mysqlTransactionManager(
//            final @Qualifier("mysqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory.getObject());
//    }
}
