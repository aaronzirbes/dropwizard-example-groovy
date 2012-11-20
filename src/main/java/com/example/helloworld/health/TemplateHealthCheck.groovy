package com.example.helloworld.health

import com.yammer.metrics.core.HealthCheck

/**
 * User: kboon
 * Date: 11/14/12
 */
class TemplateHealthCheck extends HealthCheck {

    public TemplateHealthCheck() {
        super("template");
    }

    @Override
    protected HealthCheck.Result check() throws Exception {
        return Result.healthy();
    }
}