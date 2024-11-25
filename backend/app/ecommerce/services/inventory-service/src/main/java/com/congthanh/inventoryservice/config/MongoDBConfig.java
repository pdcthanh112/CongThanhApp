package com.congthanh.inventoryservice.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.repository.mongodb")
public class MongoDBConfig extends AbstractMongoClientConfiguration {

  @Override
  protected String getDatabaseName() {
    return "CongThanhApp";
  }

  @Override
  public MongoClient mongoClient() {
    return MongoClients.create("mongodb://localhost:27017");
  }

//    @Override
//    protected String getMappingBasePackage() {
//        return "com.example.entity.mongodb";
//    }
}

