package com.caoguzelmas.kalah.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDbConfiguration {

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://kalah-player:kalah123@kalah-game-cluster.zeszsxg.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient(), "kalah-db");
    }
}
