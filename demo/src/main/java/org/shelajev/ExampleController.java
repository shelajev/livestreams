package org.shelajev;

import io.micronaut.http.annotation.*;

@Controller("/example")
public class ExampleController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}