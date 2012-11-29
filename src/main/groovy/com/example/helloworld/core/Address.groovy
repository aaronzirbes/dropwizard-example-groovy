package com.example.helloworld.core

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

/**
 * User: kboon
 * Date: 11/14/12
 */
@ToString
@Entity
@Table(name = "addresses")
@EqualsAndHashCode
class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    Long id

    @Column(name = "address1", nullable = false)
    @JsonProperty
    String address1

    @Column(name = "address2", nullable = false)
    @JsonProperty
    String address2

    @Column(name = "city", nullable = false)
    @JsonProperty
    String city

    @Column(name = "state", nullable = false)
    @JsonProperty
    String state

    @Column(name = "county", nullable = false)
    @JsonProperty
    String county

    @Column(name = "zip_code", nullable = false)
    @JsonProperty
    String zipCode
}
