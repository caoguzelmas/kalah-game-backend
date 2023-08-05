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
    String mongoDbConnectionString = System.getenv("MONGODB_CONNECTION_STRING") == null ?
            "mongodb://root:example@localhost:27017/kalah-game" : System.getenv("MONGODB_CONNECTION_STRING");
    String userName = System.getenv("MONGODB_USERNAME") == null ? "" : System.getenv("MONGODB_USERNAME");
    String password = System.getenv("MONGODB_PASSWORD") == null ? "" : System.getenv("MONGODB_PASSWORD");
    String database = System.getenv("MONGODB_DATABASE") == null ? "" : System.getenv("MONGODB_DATABASE");

    @Bean
    public MongoClient mongoClient() {
        MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());

        ConnectionString connectionString = new ConnectionString(mongoDbConnectionString);
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
