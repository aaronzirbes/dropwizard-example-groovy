package com.example.helloworld.db

import com.example.helloworld.core.Person
import groovy.mock.interceptor.MockFor
import org.junit.Before
import org.junit.Test
import org.hibernate.SessionFactory
import com.example.helloworld.HelloWorldConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.db.DatabaseConfiguration

/**
 * User: kboon
 * Date: 11/16/12
 */
class PersonDAOUnitTests {
    PersonDAO personDAO
    def sessionFactoryMock


    @Before
    public void beforeEachTest() {
        final HibernateBundle<HelloWorldConfiguration> hibernateBundle =
            new HibernateBundle<HelloWorldConfiguration>("com.example.helloworld.core") {
                @Override
                public DatabaseConfiguration getDatabaseConfiguration(HelloWorldConfiguration configuration) {
                    return configuration.getDatabaseConfiguration();
                }
            };


        personDAO = new PersonDAO(hibernateBundle.sessionFactory)
    }

    @Test
    public void create_persistsPerson() {
        Person person = new Person()

        Person persistedPerson = personDAO.create(person)

        assert persistedPerson.id
    }


}
