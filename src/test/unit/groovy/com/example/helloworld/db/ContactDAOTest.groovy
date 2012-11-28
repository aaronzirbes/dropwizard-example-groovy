package com.example.helloworld.db

import com.example.helloworld.core.Contact
import groovy.mock.interceptor.MockFor
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.junit.Test

/**
 * User: kboon
 * Date: 11/20/12
 */
class ContactDAOTest {
    @Test
    public void create_callsHibernateSession() {
        Contact contact = new Contact()

        MockFor sessionFactoryMock = new MockFor(SessionFactory)
        MockFor currentSessionMock = new MockFor(Session)

        currentSessionMock.demand.saveOrUpdate { Contact contactToPersist -> return contactToPersist }
        def currentSessionProxy = currentSessionMock.proxyDelegateInstance()

        sessionFactoryMock.demand.getCurrentSession() {  ->  currentSessionProxy }
        def sessionFactoryProxy = sessionFactoryMock.proxyDelegateInstance()

        ContactDAO contactDAO = new ContactDAO(sessionFactoryProxy)
        Contact persistedContact = contactDAO.create(contact)

        currentSessionMock.verify(currentSessionProxy)
        sessionFactoryMock.verify(sessionFactoryProxy)
        assert persistedContact == contact
    }

    @Test
    public void list_callsHibernateSessionWithNamedQuery() {
        MockFor sessionFactoryMock = new MockFor(SessionFactory)
        def sessionFactoryProxy = sessionFactoryMock.proxyDelegateInstance()

        List<Contact> expectedContacts = [new Contact(), new Contact(), new Contact()]
        ContactDAO contactDAO = new ContactDAO(sessionFactoryProxy)

        // Not super happy about this but I can't mock the hibernate stuff succesfully mock a query object.
        def expectedQuery = [:]
        boolean namedQueryCalled = false
        contactDAO.metaClass.namedQuery = { String name ->
            assert name == ContactDAO.FIND_ALL_QUERY
            namedQueryCalled = true
            return expectedQuery
        }

        boolean listCalled = false
        contactDAO.metaClass.list  = { def query ->
            assert query == expectedQuery
            listCalled = true
            return expectedContacts
        }


        List<Contact> contactList = contactDAO.list()

        assert namedQueryCalled
        assert listCalled
        assert contactList == expectedContacts
    }
}
