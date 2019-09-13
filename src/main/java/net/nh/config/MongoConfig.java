package net.nh.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import net.nh.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.Collection;

@ComponentScan(basePackages = "net.nh.repository")
@EnableMongoRepositories(basePackages = "net.nh.repository")
@Configuration
public class MongoConfig {

    @Value("${mongo.port}")
    private int port;

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.db.name}")
    protected String dbName;

    @Value("${mongo.user}")
    protected String user;

    @Value("${mongo.password}")
    protected String password;

    @Value("${mongo.connect.timeout.ms}")
    protected int connectTimeoutMs;

    @Bean
    public MongoDbFactory mongoDbFactory() {
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        builder.connectTimeout(connectTimeoutMs);
        MongoClientOptions options = builder.build();

        ServerAddress address = new ServerAddress(host, port);

        MongoCredential mongoCredential = MongoCredential.createCredential(user, dbName, password.toCharArray());
        MongoClient mongoClient = new MongoClient(address, mongoCredential, options);
        return new SimpleMongoDbFactory(mongoClient, dbName);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

}
