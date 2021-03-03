package simplewebservice;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class Person {
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getHapinessIndex() {
    return hapinessIndex;
  }

  public void setHapinessIndex(int hapinessIndex) {
    this.hapinessIndex = hapinessIndex;
  }

  private String name;
  private int hapinessIndex;

  public Person(String name, int hapinessIndex) {
    this.name = name;
    this.hapinessIndex = hapinessIndex;
  }
}
