//package com.congthanh.authservice.config;
//
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//@Configuration
//@EnableMongoRepositories(basePackages = "com.congthanh.catalogservice.repository")
//public class MongoDBConfig extends AbstractMongoClientConfiguration {
//
//  @Value("${spring.data.mongodb.database}")
//  private String databaseName;
//
//  @Override
//  protected String getDatabaseName() {
//    return databaseName;
//  }
//
//  @Override
//  public MongoClient mongoClient() {
//    return MongoClients.create("mongodb://localhost:27017");
//  }
//
////    @Override
////    protected String getMappingBasePackage() {
////        return "com.example.entity.mongodb";
////    }
//}
//
