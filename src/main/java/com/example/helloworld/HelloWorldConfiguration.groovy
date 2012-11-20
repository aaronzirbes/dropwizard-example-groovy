package com.example.helloworld

import com.fasterxml.jackson.annotation.JsonProperty
import com.yammer.dropwizard.config.Configuration
import com.yammer.dropwizard.db.DatabaseConfiguration
import org.hibernate.validator.constraints.NotEmpty

import javax.validation.Valid
import javax.validation.constraints.NotNull

/**
 * User: kboon
 * Date: 11/14/12
 */
class HelloWorldConfiguration extends Configuration {

    @NotEmpty
    String template;

    @NotEmpty
    String defaultName = "Stranger";

    @Valid
    @NotNull
    @JsonProperty
    DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
}
