package com.example.helloworld

import org.junit.Test
import com.yammer.dropwizard.config.Bootstrap
import com.example.helloworld.cli.RenderCommand
import com.yammer.dropwizard.assets.AssetsBundle
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.config.Configuration
import com.yammer.dropwizard.json.ObjectMapperFactory
import groovy.mock.interceptor.MockFor
import com.yammer.dropwizard.hibernate.HibernateBundle
import org.junit.Ignore

/**
 * Just trying to get a feel for what is worthwhile testing in a unit sense.
 *
 * User: kboon
 * Date: 11/16/12
 */
class HelloWorldServiceUnitTests {
    MockFor hibernateBundleMock

    @Test
    public void initialize_addsToBootstrap() {
        HelloWorldService helloWorldService = new HelloWorldService()
        Bootstrap<HelloWorldConfiguration> bootstrap = new Bootstrap<HelloWorldConfiguration>(helloWorldService)

        helloWorldService.initialize(bootstrap)

        assert bootstrap.commands.contains(helloWorldService.renderCommand)

        assert bootstrap.bundles.contains(helloWorldService.assetsBundle)
        //assert bootstrap.bundles.contains(helloWorldService.hibernateBundle)
        //assert bootstrap.bundles.contains(helloWorldService.migrationsBundle)
    }

    @Ignore
    @Test
    public void run_addsToEnvironment() {
        hibernateBundleMock = new MockFor(HibernateBundle)

        HelloWorldService helloWorldService = new HelloWorldService()
        HelloWorldConfiguration helloWorldConfiguration = new HelloWorldConfiguration()
        Environment environment = new Environment("", helloWorldConfiguration, new ObjectMapperFactory())

        helloWorldService.run(helloWorldConfiguration, environment)
    }
}
