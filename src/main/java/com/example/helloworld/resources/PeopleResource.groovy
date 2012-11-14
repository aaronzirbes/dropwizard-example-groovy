package com.example.helloworld.resources

import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import com.example.helloworld.db.PersonDAO
import javax.ws.rs.POST
import com.example.helloworld.core.Person
import javax.ws.rs.GET
import com.yammer.dropwizard.hibernate.Transactional

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
public class PeopleResource {

    private final PersonDAO peopleDAO;

    public PeopleResource(PersonDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @POST
    @Transactional
    public Person createPerson(Person person) {
        return peopleDAO.create(person);
    }

    @GET
    @Transactional
    public List<Person> listPeople() {
        return peopleDAO.findAll();
    }

}