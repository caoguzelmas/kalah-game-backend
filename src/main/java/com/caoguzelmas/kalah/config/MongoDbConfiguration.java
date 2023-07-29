package com.caoguzelmas.kalah.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDbConfiguration {
    // TODO Should be updated for local connection
    String mongoDbConnectionString = System.getenv("MONGODB_CONNECTION_STRING");

/*    @Bean
    public MongoClient mongoClient() {
        //return MongoClients.create();


        *//*ConnectionString connectionString = new ConnectionString("mongodb://root:example@localhost:27017/kalah-game");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);*//*
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {

        return new MongoTemplate(mongoClient(), "kalah-db");
    }*/
}
