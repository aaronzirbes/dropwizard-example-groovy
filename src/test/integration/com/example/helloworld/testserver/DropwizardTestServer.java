package com.example.helloworld.testserver;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.cli.Cli;
import com.yammer.dropwizard.cli.ServerCommand;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.net.URI;

/**
 * JUnit @Rule that'll start and stop a Dropwizard service around each test method.
 *
 * This class might be extended with factory methods for pre-configured http client
 * instance for both the main and the internal service endpoint.
 *
 * originally written by Kim A. Betti <kim@developer-b.com>
 * all bugs introduced by me, Alexander Reelsen <alexander@reelsen.net>
 */
public class DropwizardTestServer<C extends Configuration, S extends Service<C>> implements TestRule {

    private final Class<C> configurationClass;
    private final Class<S> serviceClass;
    private final String config;

    private TestableServerCommand<C> command;
    private S service;

    protected DropwizardTestServer(Class<C> configClass, Class<S> serviceClass, String config) {
        this.configurationClass = configClass;
        this.serviceClass = serviceClass;
        this.config = config;
    }

    public static <C extends Configuration, S extends Service<C>> DropwizardTestServer<C, S> testServer(
            Class<C> configClass, Class<S> serviceClass, String config) {
        return new DropwizardTestServer<C, S>(configClass, serviceClass, config);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new DropwizardStatement(base);
    }

    public boolean isRunning() {
        return command.isRunning();
    }

    public S getService() {
        return service;
    }

    public URI getPublicRootUri() {
        return command.getRootUriForConnector("main");
    }

    public URI getInternalRootUri() {
        return command.getRootUriForConnector("internal");
    }

    private class DropwizardStatement extends Statement {

        private final Statement base;

        public DropwizardStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            service = serviceClass.newInstance();
            command = new TestableServerCommand<C>(service);

            try {
                String[] arguments = new String[] { "test-server", config };
                final Bootstrap<C> bootstrap = new Bootstrap<C>(service);
                bootstrap.addCommand(new ServerCommand<C>(service));
                bootstrap.addCommand(command);
                //bootstrap.addBundle(new AssetsBundle());
                final Cli cli = new Cli(this.getClass(), bootstrap);
                cli.run(arguments);

                base.evaluate();
            }
            finally {
                command.stop();
            }
        }
    }
}
