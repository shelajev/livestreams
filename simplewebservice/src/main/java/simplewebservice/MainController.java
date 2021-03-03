package simplewebservice;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.annotation.PostConstruct;

@Controller("/")
public class MainController {

  @Value("${micronaut.application.greeting}")
  private String greeting;

  private MyService myservice;

  public MainController(MyService myservice) {
    this.myservice = myservice;
  }

  @PostConstruct
  public void postContruct ( ) {
    System.out.println("MainController postConstruct : "  + myservice);
  }

  @Get("/hello/{name}")
  public String hello(String name) {
    return greeting + " " + name + "!";
  }
}
