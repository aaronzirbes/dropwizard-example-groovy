package com.example.helloworld.com.example.helloworld.core

import org.junit.Test
import com.example.helloworld.core.Person
import static com.yammer.dropwizard.testing.JsonHelpers.*
import org.junit.Before
import com.example.helloworld.core.Address

/**
 * User: kboon
 * Date: 11/16/12
 */
class PersonUnitTests {
    Person person

    @Before
    public void setUp() {
        Address address = new Address(id: 0, address1: "15 South 5th Street", address2: "", city: "Minneapolis", state: "MN", county: "Hennepin", zipCode: "55402")
        person = new Person(name: "Luther Blissett", jobTitle:"senior vice chairman director dude", address: address);
    }

    @Test
    public void serializesToJSON() throws Exception {
        assert asJson(person) == jsonFixture("fixtures/person.json")
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        assert fromJson(jsonFixture("fixtures/person.json"), Person.class) == person
    }

    @Test
    public void equals_returnsTrueWhenEqual() {
        Address address = new Address(address1: "15 South 5th Street", address2: "", city: "Minneapolis", state: "MN", county: "Hennepin", zipCode: "55402")
        Person otherPerson = new Person(name: "Luther Blissett", jobTitle:"senior vice chairman director dude", address: address);

        assert otherPerson.equals(person)
    }

    @Test
    public void equals_returnsFalseWhenEqual() {
        Address address = new Address(address1: "15 South 5th Street", address2: "", city: "Minneapolis", state: "MN", county: "Hennepin", zipCode: "55402")
        Person otherPerson = new Person(name: "Some Dude", jobTitle:"senior vice chairman director dude", address: address);

        assert !otherPerson.equals(person)
    }
}
