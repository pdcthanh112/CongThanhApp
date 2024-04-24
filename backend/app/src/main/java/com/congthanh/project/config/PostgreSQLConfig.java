package com.congthanh.project.config;

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
@EnableJpaRepositories(basePackages = "com.congthanh.project.repository",
        entityManagerFactoryRef = "postgresqlEntityManagerFactory",
        transactionManagerRef = "postgresqlTransactionManager")
public class PostgreSQLConfig {

  @Primary
  @Bean
  @ConfigurationProperties(prefix = "spring.postgresql.datasource")
  public DataSource postgresqlDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean
  public LocalContainerEntityManagerFactoryBean postgresqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {

    HashMap<String, Object> properties = new HashMap<>();
    properties.put("spring.jpa.hibernate.ddl-auto=update", "update");
    return builder
            .dataSource(postgresqlDataSource())
            .properties(properties)
            .packages("com.congthanh.project.entity")
            .persistenceUnit("postgresql")
            .build();
  }

  @Primary
  @Bean
  public PlatformTransactionManager postgresqlTransactionManager(
          final @Qualifier("postgresqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory.getObject());
  }
}

