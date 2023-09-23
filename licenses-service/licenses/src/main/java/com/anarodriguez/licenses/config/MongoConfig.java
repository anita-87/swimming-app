package com.anarodriguez.licenses.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

import static java.util.Collections.singletonList;

@Configuration
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Value("${mongodb.db}")
    private String databaseName;

    @Value("${mongodb.username}")
    private String userName;

    @Value("${mongodb.password}")
    private String password;

    @Value("${mongodb.host}")
    private String host;

    @Value("${mongodb.port}")
    private int port;

    @Value("${mongodb.authSource}")
    private String authSource;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder.credential(MongoCredential.createCredential(
                        userName, authSource, password.toCharArray()))
                .applyToClusterSettings(settings -> {
                    settings.hosts(singletonList(
                            new ServerAddress(host, port)
                    ));
                });
    }
}
