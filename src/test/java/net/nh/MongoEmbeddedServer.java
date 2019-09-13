package net.nh;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.net.ServerSocket;

public class MongoEmbeddedServer {

    private static final Logger LOG = LoggerFactory.getLogger(MongoEmbeddedServer.class);

    public static final String host = "localhost";
    private int port;
    private static final MongodStarter starter = MongodStarter.getDefaultInstance();
    private MongodExecutable _mongodExe;
    private MongodProcess _mongod;

    public MongoEmbeddedServer() {
    }

    private void init() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            port = socket.getLocalPort();
        } catch (IOException e) {
            LOG.error("Unable to find free port", e);
        }

        LOG.info("Starting embedded Mongo on port {}", port);

        try {
            _mongodExe = starter.prepare(new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(host, port, Network.localhostIsIPv6()))
                    .build());
        } catch (Exception e) {
            LOG.error("Unable to start Mongo executable", e);
            throw new RuntimeException("Unable to start Mongo executable", e);
        }

        try {
            _mongod = _mongodExe.start();
        } catch (Exception e) {
            LOG.error("Unable to start embedded Mongo daemon", e);
            _mongodExe.stop();
            throw new RuntimeException("Unable to start Mongo daemon", e);
        }

        LOG.info("Embedded Mongo started successfully");
    }

    public void destroy() {
        LOG.info("Stopping Mongo daemon process");
        try {
            _mongod.stop();
        } catch (Exception e) {
            LOG.error("Error stopping Mongo daemon process", e);
            throw new RuntimeException(e);
        }

        LOG.info("Stopping Mongo executable process");
        try {
            _mongodExe.stop();
        } catch (Exception e) {
            LOG.error("Error stopping Mongo executable", e);
            throw new RuntimeException(e);
        }
    }

    public int getPort() {
        return port;
    }
}
