package com.example.helloworld.resources

import org.junit.Test
import com.example.helloworld.core.Contact
import com.example.helloworld.db.ContactDAO
import groovy.mock.interceptor.MockFor
import org.eclipse.jetty.server.SessionManager
import org.hibernate.SessionFactory

/**
 * User: kboon
 * Date: 11/19/12
 */
class ContactResourceUnitTests {
    @Test
    public void contactResource_callsHibernateDAO() {
        Contact expectedContact = new Contact()

        MockFor contactDAOMock = new MockFor(ContactDAO)
        MockFor sessionFactory = new MockFor(SessionFactory)

        contactDAOMock.demand.create() { Contact contact ->
            assert contact == expectedContact
            return contact
        }

        ContactDAO mockDAOInstance = contactDAOMock.proxyInstance(sessionFactory.proxyInstance())

        ContactResource contactResource = new ContactResource(mockDAOInstance)

        Contact newContact  = contactResource.createContact(expectedContact)

        assert newContact
        contactDAOMock.verify(contactResource.contactDAO)

    }
}
