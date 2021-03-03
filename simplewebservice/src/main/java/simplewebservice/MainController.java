package simplewebservice;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/")
public class MainController {


  @Value("${micronaut.application.greeting}")
  private String greeting;

  @Get("/hello/{name}")
  public String hello(String name) {
    return greeting + " " + name + "!";
  }
}
