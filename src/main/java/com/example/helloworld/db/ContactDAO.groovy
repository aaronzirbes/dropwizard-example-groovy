package com.example.helloworld.db

import com.example.helloworld.core.Contact
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

/**
 * User: kboon
 * Date: 11/19/12
 */
// TODO: write a unit test for this class
class ContactDAO extends AbstractDAO<Contact> {

    protected static final String FIND_ALL_QUERY = "com.example.helloworld.core.Contact.findAll"

    public ContactDAO(SessionFactory factory) {
        super(factory);
    }

    public Contact create(Contact contact) {
        return persist(contact)
    }

    public List<Contact> list() {
        return list(namedQuery(FIND_ALL_QUERY));
    }
}
