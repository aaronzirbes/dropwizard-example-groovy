package com.example.helloworld.db;

import com.example.helloworld.ContactsConfiguration;
import com.example.helloworld.ContactsService;
import com.example.helloworld.core.Address;
import com.example.helloworld.core.Contact;
import com.example.helloworld.testserver.DropwizardTestServer;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.SessionFactoryFactory;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.example.helloworld.testserver.DropwizardTestServer.testServer;
import static org.mockito.Mockito.mock

public class ContactDAOIntegrationTest extends DropwizardDAOTest{

    SessionFactory sessionFactory

    @Override
    protected SessionFactory getFactory(DatabaseConfiguration dbConfig) throws Exception {

        final SessionFactoryFactory factory = new SessionFactoryFactory()
        final Environment environment = mock(Environment.class)

        try {
            sessionFactory = factory.build(environment,
                    dbConfig,
                    [Contact.class,Address.class])
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e)
        }

        sessionFactory
    }

    @Before
    public void setUp() throws Exception {
        super.setUp()
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown()
    }

    @Rule
    public DropwizardTestServer<ContactsConfiguration, ContactsService> testServer = testServer(ContactsConfiguration.class, ContactsService.class, "example.yml");

    @Test
    public void list_callsHibernateSessionWithNamedQuery() {

        ContactDAO contactDAO = new ContactDAO(sessionFactory)

        Address persistedAddressA = new Address(address1: "123 Lane", city: "Minneapolis", state: "MN", county: "Henipen", zipCode: "55041")
        Address persistedAddressB = new Address(address1: "456 Street", city: "St. Paul", state: "MN", county: "Ramsey", zipCode: "55066")
        Contact persistedContactA = contactDAO.create(new Contact(firstName: "Chad", lastName: "Small", jobTitle: "Dev", phoneNumber: "999-88-7777", address: persistedAddressA))
        Contact persistedContactB = contactDAO.create(new Contact(firstName: "Kyle", lastName: "Boon", jobTitle: "Dev", phoneNumber: "999-88-7778", address: persistedAddressA))
        Contact persistedContactC = contactDAO.create(new Contact(firstName: "Ryley", lastName: "Gahagan", jobTitle: "Dev", phoneNumber: "999-88-7779", address: persistedAddressB))
        List<Contact> expectedContacts = [persistedContactA, persistedContactB, persistedContactC]

        List<Contact> contactList = contactDAO.list()

        assert 3 == contactList.size()
        assert contactList == expectedContacts
    }

    @Test
    public void list_callsHibernateSessionWithNamedQuery2() {

        ContactDAO contactDAO = new ContactDAO(sessionFactory)

        Address persistedAddressA = new Address(address1: "123 Lane", city: "Minneapolis", state: "MN", county: "Henipen", zipCode: "55041")
        Address persistedAddressB = new Address(address1: "456 Street", city: "St. Paul", state: "MN", county: "Ramsey", zipCode: "55066")
        Contact persistedContactA = contactDAO.create(new Contact(firstName: "Chad", lastName: "Small", jobTitle: "Dev", phoneNumber: "999-88-7777", address: persistedAddressA))
        Contact persistedContactC = contactDAO.create(new Contact(firstName: "Ryley", lastName: "Gahagan", jobTitle: "Dev", phoneNumber: "999-88-7779", address: persistedAddressB))
        List<Contact> expectedContacts = [persistedContactA, persistedContactC]

        List<Contact> contactList = contactDAO.list()

        assert 2 == contactList.size()
        assert contactList == expectedContacts
    }

}
