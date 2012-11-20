package com.example.helloworld.auth

import com.example.helloworld.core.User
import com.google.common.base.Optional
import com.yammer.dropwizard.auth.AuthenticationException
import com.yammer.dropwizard.auth.Authenticator
import com.yammer.dropwizard.auth.basic.BasicCredentials

/**
 * User: kboon
 * Date: 11/14/12
 */
class ExampleAuthenticator implements Authenticator<BasicCredentials, User> {
    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if ("secret".equals(credentials.getPassword())) {
            return Optional.of(new User(name: credentials.getUsername()));
        }
        return Optional.absent();
    }
}
