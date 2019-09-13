package net.nh;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import net.nh.config.MongoConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

public class MongoTestConfig extends MongoConfig {

    @Value("#{mongoEmbeddedServer.getPort()}")
    private int port;

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public MongoEmbeddedServer mongoEmbeddedServer() {
        return new MongoEmbeddedServer();
    }

    @Override
    public MongoDbFactory mongoDbFactory() {
        MongoClient mongoClient = new MongoClient(MongoEmbeddedServer.host, port);
        return new SimpleMongoDbFactory(mongoClient, dbName);
    }
}
