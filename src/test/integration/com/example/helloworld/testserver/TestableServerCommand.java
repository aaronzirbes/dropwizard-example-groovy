package com.example.helloworld.testserver;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.cli.ConfiguredCommand;
import com.yammer.dropwizard.config.*;
import com.yammer.dropwizard.validation.Validator;
import net.sourceforge.argparse4j.inf.Namespace;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.net.URISyntaxException;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Normally ServerCommand is in charge of starting the service, but that's not particularly
 * well suited for integration testing as it joins the current thread and keeps the Server
 * instance to itself.
 *
 * This implementation is based on the original ServerCommand, but in addition to being
 * stoppable it provides a few convenience methods for tests.
 *
 * originally written by Kim A. Betti <kim@developer-b.com>
 * all bugs introduced by me, Alexander Reelsen <alexander@reelsen.net>
 */
public class TestableServerCommand<T extends Configuration> extends ConfiguredCommand<T> {

    private final Logger logger = LoggerFactory.getLogger(TestableServerCommand.class);

    private final Class<T> configurationClass;

    private Server server;
    private Service<T> service;

    public TestableServerCommand(Service<T> service) {
        super("test-server", "Starts an HTTP test-server running the service");
        this.service = service;
        this.configurationClass = service.getConfigurationClass();
    }

    @Override
    protected Class<T> getConfigurationClass() {
        return configurationClass;
    }

    @Override
    protected void run(Bootstrap<T> bootstrap, Namespace namespace, T configuration) throws Exception {
        final Environment environment = new Environment(bootstrap.getName(),
                configuration,
                bootstrap.getObjectMapperFactory().copy(),
                new Validator());

        service.initialize(bootstrap);
        bootstrap.runWithBundles(configuration, environment);
        service.run(configuration, environment);
        server = initializeServer(service, configuration, environment);

        try {
            server.start();
        }
        catch (Exception e) {
            logger.error("Unable to start test-server, shutting down", e);
            server.stop();
        }
    }

    public void stop() throws Exception {
        try {
            stopJetty();
        }
        finally {
            unRegisterLoggingMBean();
        }
    }

    /**
     * We won't be able to run more then a single test in the same JVM instance unless
     * we do some tidying and un-register a logging m-bean added by Dropwizard.
     */
    private void unRegisterLoggingMBean() throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName loggerObjectName = new ObjectName("com.yammer:type=Logging");
        if (server.isRegistered(loggerObjectName)) {
            server.unregisterMBean(loggerObjectName);
        }
    }

    private void stopJetty() throws Exception {
        if (server != null) {
            server.stop();
            checkArgument(server.isStopped());
        }
    }

    public boolean isRunning() {
        return server.isRunning();
    }

    public URI getRootUriForConnector(String connectorName) {
        try {
            Connector connector = getConnectorNamed(connectorName);
            String host = connector.getHost() != null ? connector.getHost() : "localhost";
            return new URI("http://" + host + ":" + connector.getPort());
        }
        catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    private Connector getConnectorNamed(String name) {
        Connector[] connectors = server.getConnectors();
        for (Connector connector : connectors) {
            if (connector.getName().equals(name)) {
                return connector;
            }
        }

        throw new IllegalStateException("No connector named " + name);
    }

    private Server initializeServer(Service<T> service, T configuration, Environment environment) throws Exception {
        ServerFactory serverFactory = getServerFactory(service, configuration);

        return serverFactory.buildServer(environment);
    }

    private ServerFactory getServerFactory(Service<T> service, T configuration) {
        HttpConfiguration httpConfig = configuration.getHttpConfiguration();
        return new ServerFactory(httpConfig, "test");
    }

}
