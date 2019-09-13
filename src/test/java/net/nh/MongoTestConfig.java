package net.nh;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import net.nh.config.MongoConfig;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

public class MongoTestConfig extends MongoConfig {

    @Override
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(MongoRepositoryTestBase.client(), dbName);
    }
}
