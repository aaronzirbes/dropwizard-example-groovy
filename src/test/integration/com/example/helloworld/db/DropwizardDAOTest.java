package com.example.helloworld.db;

import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.migrations.ManagedLiquibase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.junit.After;
import org.junit.Before;

public abstract class DropwizardDAOTest {

    protected abstract SessionFactory getFactory(DatabaseConfiguration dbConfig) throws Exception;
    private Session session;
    private DatabaseConfiguration dbConfig;
    private ManagedLiquibase managedLiquibase;

    @Before
    public void setUp() throws Exception
    {
        dbConfig = getDatabaseConfiguration();
        session = this.getFactory(dbConfig).openSession();
        ManagedSessionContext.bind(session);
        managedLiquibase = new ManagedLiquibase(dbConfig);
        managedLiquibase.update("");
    }

    @After
    public void tearDown() throws Exception
    {
        managedLiquibase.stop();

        session.close();
        ManagedSessionContext.unbind(getFactory(dbConfig));
    }

    protected DatabaseConfiguration getDatabaseConfiguration() {
        DatabaseConfiguration dbConfig = new DatabaseConfiguration();

        //dbConfig.setUrl("jdbc:hsqldb:mem:DbTest-" + System.nanoTime());
        dbConfig.setUrl("jdbc:h2:target/example-" + System.nanoTime());
        dbConfig.setUser("sa");
        //dbConfig.setDriverClass("org.hsqldb.jdbcDriver");
        dbConfig.setDriverClass("org.h2.Driver");
        dbConfig.setValidationQuery("SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_USERS");

        return dbConfig;
    }
}
