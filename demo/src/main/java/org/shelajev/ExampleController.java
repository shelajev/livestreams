package org.shelajev;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.annotation.*;
import io.micronaut.runtime.context.scope.Refreshable;

import javax.inject.Inject;

@Refreshable
@Controller("/example")
public class ExampleController {

    @Inject
    ApplicationContext context;

    @Get("/config/{key}")
    public String config(String key) {
        return context.get(key, String.class).orElse("NOTHING");
    }

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}