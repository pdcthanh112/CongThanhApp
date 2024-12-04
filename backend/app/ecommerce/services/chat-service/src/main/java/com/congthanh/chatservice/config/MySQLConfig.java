package com.congthanh.chatservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
