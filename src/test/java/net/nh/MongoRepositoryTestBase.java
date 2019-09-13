package net.nh;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import net.nh.config.PropertyConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.ServerSocket;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoTestConfig.class, PropertyConfig.class})
public class MongoRepositoryTestBase {

    private static final Logger LOG = LoggerFactory.getLogger(MongoRepositoryTestBase.class);
    private static final MongodStarter starter = MongodStarter.getDefaultInstance();

    private static int port;
    private static final String host = "localhost";
    private static MongodExecutable _mongodExe;
    private static MongodProcess _mongod;
    private static MongoClient _mongoClient;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MongoTemplate mongoTemplate;

    static {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            port = socket.getLocalPort();
        } catch (IOException e) {
            LOG.error("Unable to find free port", e);
        }
    }

    @BeforeClass
    public static void setUp() throws Exception {
        _mongodExe = starter.prepare(new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(host, port, Network.localhostIsIPv6()))
                .build());

        LOG.info("Starting Mongo daemon on port {}", port);
        _mongod = _mongodExe.start();

        _mongoClient = new MongoClient(host, port);
    }

    @AfterClass
    public static void tearDown() {
        try {
            _mongod.stop();
        } catch (Exception e) {
            LOG.error("Error stopping Mongo daemon process", e);
            throw new RuntimeException(e);
        }

        try {
            _mongodExe.stop();
        } catch (Exception e) {
            LOG.error("Error stopping Mongo executable", e);
            throw new RuntimeException(e);
        }
    }

    @After
    public void dropDatabase() {
        mongoTemplate.getDb().drop();
    }

    public static MongoClient client() {
        return _mongoClient;
    }
}
