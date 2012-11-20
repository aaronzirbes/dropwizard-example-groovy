package com.example.helloworld.core

/**
 * User: kboon
 * Date: 11/14/12
 */


import javax.persistence.*
import com.fasterxml.jackson.annotation.JsonProperty

@Entity
@Table(name = "people")
@NamedQueries([
    @NamedQuery(
    name = "com.example.helloworld.core.Person.findAll",
    query = "SELECT p FROM Person p"
    ),
    @NamedQuery(
    name = "com.example.helloworld.core.Person.findById",
    query = "SELECT p FROM Person p WHERE p.id = :id"
    )
])
class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    long id;

    @Column(name = "fullName", nullable = false)
    @JsonProperty
    String name;

    @Column(name = "jobTitle", nullable = false)
    @JsonProperty
    String jobTitle;

    @Column(name = "address")
    @JsonProperty
    Address address

    public boolean equals(Person otherPerson) {
        return (otherPerson?.jobTitle == this?.jobTitle
                && otherPerson?.name == this?.name
        && otherPerson?.address == this?.address)
    }
}
