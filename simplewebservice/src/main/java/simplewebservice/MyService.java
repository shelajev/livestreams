package simplewebservice;

import javax.inject.Singleton;

@Singleton
public class MyService {

  public Object getOleg() {
  return new Person("Oleg",42);
}
}
