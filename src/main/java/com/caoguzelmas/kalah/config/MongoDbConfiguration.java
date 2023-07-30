package com.caoguzelmas.kalah.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

@Configuration
public class MongoDbConfiguration {
    // TODO Should be updated for local connection
    String mongoDbConnectionString = System.getenv("MONGODB_CONNECTION_STRING");
    String userName = System.getenv("MONGODB_USERNAME");
    String password = System.getenv("MONGODB_PASSWORD");
    String database = System.getenv("MONGODB_DATABASE");

    @Bean
    public MongoClient mongoClient() {
        //return MongoClients.create();
        System.out.println("MONGODB_CONNECTION_STRING== " + mongoDbConnectionString);
        System.out.println("MONGODB_USERNAME== " + userName);
        System.out.println("MONGODB_PASSWORD== " + password);
        System.out.println("MONGODB_DATABASE== " + database);

        MongoCredential credential = MongoCredential.createCredential("kalah-player123", "kalah-db", "pwd123".toCharArray());

        //ConnectionString connectionString = new ConnectionString("mongodb://kalah-player:pwd123@localhost:27017/");
        ConnectionString connectionString = new ConnectionString("mongodb://kalah-player:pwd123@localhost:27017/");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {

        return new MongoTemplate(mongoClient(), "kalah-db");
    }
}
