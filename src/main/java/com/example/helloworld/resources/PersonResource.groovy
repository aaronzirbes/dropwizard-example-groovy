package com.example.helloworld.resources

import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import com.example.helloworld.db.PersonDAO
import javax.ws.rs.GET
import com.example.helloworld.core.Person
import javax.ws.rs.PathParam
import com.yammer.dropwizard.jersey.params.LongParam
import com.yammer.dropwizard.hibernate.Transactional
import com.google.common.base.Optional
import javassist.NotFoundException

@Path("/people/{personId}")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    private final PersonDAO peopleDAO;

    public PersonResource(PersonDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @GET
    @Transactional
    public Person getPerson(@PathParam("personId") LongParam personId) {
        final Optional<Person> person = peopleDAO.findById(personId.get());
        if (!person.isPresent()) {
            throw new NotFoundException("No such user.");
        }
        return person.get();
    }

}