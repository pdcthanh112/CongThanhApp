package com.congthanh.cartservice.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.congthanh.cartservice.repository")
public class MongoDBConfig extends AbstractMongoClientConfiguration {

  @Override
  protected String getDatabaseName() {
    return "CongThanhApp-Ecommerce";
  }

  @Override
  public MongoClient mongoClient() {
    return MongoClients.create("mongodb://localhost:27017");
  }

  @Bean
  public MongoClientSettings mongoClientSettings() {
    return MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
            .build();
  }

//    @Override
//    protected String getMappingBasePackage() {
//        return "com.example.entity.mongodb";
//    }
}

