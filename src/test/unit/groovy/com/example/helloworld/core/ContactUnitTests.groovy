package com.example.helloworld.core
import com.example.helloworld.core.Contact
import org.junit.Before
import org.junit.Test

import static com.yammer.dropwizard.testing.JsonHelpers.*
import com.example.helloworld.core.Address

/**
 * User: kboon
 * Date: 11/19/12
 */
class ContactUnitTests {
    Address address
    Contact contact

    @Before
    public void setUp() {
        address = new Address(
            id:0,
            address1:"15 South 5th Street",
            address2:"",
            city:"Minneapolis",
            state:"MN",
            county:"Hennepin",
            zipCode:"55402"
         )

        contact = new Contact(
                id:0,
                firstName:"Kyle",
                lastName:"Boon",
                jobTitle:"developer",
                phoneNumber:"999-999-9999",
                address: address
        )
    }

    @Test
    public void serializesToJSON() throws Exception {
        assert asJson(contact) == jsonFixture("fixtures/contact.json")
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        assert fromJson(jsonFixture("fixtures/contact.json"), Contact.class) == contact
    }
}
