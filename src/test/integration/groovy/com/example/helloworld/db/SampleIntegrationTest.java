package com.example.helloworld.db;

import com.example.helloworld.ContactsConfiguration;
import com.example.helloworld.ContactsService;
import com.example.helloworld.core.Address;
import com.example.helloworld.core.Contact;
import com.example.helloworld.testserver.DropwizardTestServer;
//import com.google.inject.Injector;

//import Sample.server.SampleConfiguration;

//import org.apache.http.client.HttpClient;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.ResponseHandler;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.BasicResponseHandler;
import com.google.common.collect.ImmutableList;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.SessionFactoryFactory;
import org.hibernate.SessionFactory;
import org.junit.*;

import java.net.URI;
import java.net.URISyntaxException;

import static com.example.helloworld.testserver.DropwizardTestServer.testServer;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class SampleIntegrationTest extends DropwizardDAOTest{

    @Override
    protected SessionFactory getFactory(DatabaseConfiguration dbConfig) throws Exception {


        SessionFactory sessionFactory;
        final SessionFactoryFactory factory = new SessionFactoryFactory();
        final Environment environment = mock(Environment.class);

        try {
            sessionFactory = factory.build(environment,
                    dbConfig,
                    ImmutableList.<Class<?>>of(Contact.class,Address.class));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return sessionFactory;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Rule
    public DropwizardTestServer<ContactsConfiguration, ContactsService> testServer
            = testServer(ContactsConfiguration.class, ContactsService.class, "example.yml");

    @Test
    public void shouldBeRunning() {
        assertTrue("Server should be running", testServer.isRunning());
    }

    @Test
    public void weHaveAccessToPublicRootUri() throws URISyntaxException {
        URI expectedUri = new URI("http://localhost:8080");
        assertEquals(expectedUri, testServer.getPublicRootUri());
    }

    @Test
    public void weAlsoHaveAccessToInternalRootUri() throws URISyntaxException {
        URI expectedUri = new URI("http://localhost:8081");
        assertEquals(expectedUri, testServer.getInternalRootUri());
    }

}
