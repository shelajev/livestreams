
package me.opc.se.bare;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import io.helidon.config.Config;
import io.helidon.webclient.WebClient;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.Proxy;


public class GreetService implements Service {
    private static final Logger LOGGER = Logger.getLogger(GreetService.class.getName());
  private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());


  public static final String JS_CODE = "(function parseBeers(data, name) {" +
    "    var beers = JSON.parse(data);" +
    "    var results = [];" +
    "    for (var b of beers) {" +
    "        if (b.name.indexOf(name) != -1) {" +
    "            results.push({name: b.name, desc: b.description});" +
    "        }" +
    "    }" +
    "    return JSON.stringify(results);" +
    "})";

    private final String greeting;

  GreetService(Config config) {
    greeting = config.get("app.greeting").asString().orElse("Ciao");
  }

  /**
   * A service registers itself by updating the routing rules.
   *
   * @param rules the routing rules.
   */
  @Override
  public void update(Routing.Rules rules) {
    rules.get("/{what}", this::getDefaultMessageHandler);
  }

  /**
   * Return a worldly greeting message.
   *
   * @param request  the server request
   * @param response the server response
   */
  private void getDefaultMessageHandler(ServerRequest request, ServerResponse response) {
    try {
      var what = request.path().param("what");


      Context ctx = Context.newBuilder("js")
        .allowAllAccess(true)
        .option("inspect", "4242")
        .option("inspect.Path", java.util.UUID.randomUUID().toString())
        .build();

      var client = WebClient.builder().baseUri("https://api.punkapi.com/v2/beers").build();
      String result = client.get().request(String.class).toCompletableFuture().get();

      var parseBeers = ctx.eval("js", JS_CODE);

      response.send(parseBeers.execute(result, what).asString());
    } catch (Exception ignoreMe) {
      throw new RuntimeException(ignoreMe);
    }
  }
}
