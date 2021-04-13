
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
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.Proxy;


public class GreetService implements Service {

  private ThreadLocal<Value> parseBeers = ThreadLocal.withInitial(() -> {
    try {
      Context ctx = Context.newBuilder("js")
        .allowAllAccess(true).fileSystem(new MyFileSystem())
        .build();

      Source source = Source.newBuilder("js",
        "import {parseBeersAsync as parse} from 'parseBeers'; parse;", "loading.mjs").build();

      return ctx.eval(source);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  });


  private static final Logger LOGGER = Logger.getLogger(GreetService.class.getName());
  private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

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


      Thenable fetchBeerData = (onResolve, onReject) -> {
        try {
          var client = WebClient.builder().baseUri("https://api.punkapi.com/v2/beers").build();
          String result = client.get().request(String.class).toCompletableFuture().get();
          onResolve.executeVoid(result);
        } catch (Exception e) {
          onReject.executeVoid(e);
        }
      };

      response.send(parseBeers.get().execute(fetchBeerData, what).asString());

    } catch (Exception ignoreMe) {
      throw new RuntimeException(ignoreMe);
    }
  }
}
