package simplewebservice;

import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] args) {

        System.out.println("Hello twitch !");
        Micronaut.run(Application.class, args);
    }
}
