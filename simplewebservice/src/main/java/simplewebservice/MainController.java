package simplewebservice;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/")
public class MainController {

  @Get("/hello/{name}")
  public String hello(String name) {
    return "Hello " + name + "!";
  }
}
