package com.example.helloworld.resources

import com.example.helloworld.core.Contact
import com.example.helloworld.db.ContactDAO

import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import com.yammer.dropwizard.hibernate.UnitOfWork
import javax.ws.rs.GET
import com.example.helloworld.core.Person

/**
 * User: kboon
 * Date: 11/19/12
 */
@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
class ContactResource {
    private final ContactDAO contactDAO

    public ContactResource(ContactDAO contactDAO) {
        this.contactDAO = contactDAO
    }

    //TODO: I had to remove the @Transactional Annotation because maven couldn't find com.yammer.dropwizard.hibernate.Transactional. Need to check Coda's example.
    @POST
    @UnitOfWork
    public Contact createContact(Contact deserializedContact) {
        return contactDAO.create(deserializedContact)
    }

    @GET
    @UnitOfWork
    public List<Contact> listContact() {
        return contactDAO.findAll();
    }
}
