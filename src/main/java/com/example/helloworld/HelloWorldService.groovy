package com.example.helloworld

import com.example.helloworld.cli.RenderCommand
import com.example.helloworld.db.ContactDAO
import com.example.helloworld.resources.ContactResource
import com.yammer.dropwizard.Service
import com.yammer.dropwizard.assets.AssetsBundle
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.migrations.MigrationsBundle
import com.example.helloworld.core.Contact
import com.example.helloworld.core.Address

/**
 * User: kboon
 * Date: 11/14/12
 */
class HelloWorldService extends Service<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldService().run(args)
    }

    HibernateBundle<HelloWorldConfiguration> hibernateBundle =
        new HibernateBundle<HelloWorldConfiguration>([Contact, Address]) {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(HelloWorldConfiguration configuration) {
                return configuration.getDatabaseConfiguration();
            }
        }

    MigrationsBundle<HelloWorldConfiguration> migrationsBundle =
        new MigrationsBundle<HelloWorldConfiguration>() {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(HelloWorldConfiguration configuration) {
                return configuration.getDatabaseConfiguration();
            }
        }

    RenderCommand renderCommand = new RenderCommand()
    AssetsBundle assetsBundle = new AssetsBundle()

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.name = "hello-world"

        bootstrap.addCommand renderCommand
        bootstrap.addBundle assetsBundle
        bootstrap.addBundle migrationsBundle
        bootstrap.addBundle hibernateBundle
    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {

        final ContactDAO contactDAO = new ContactDAO(hibernateBundle.getSessionFactory())
        environment.addResource(new ContactResource(contactDAO))
    }
}
