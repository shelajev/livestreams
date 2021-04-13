
package me.opc.se.bare;

import java.io.File;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import io.helidon.common.http.MediaType;
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
        .allowAllAccess(true)
        .allowExperimentalOptions(true)
        .option("js.global-property", "true")
        .fileSystem(new MyFileSystem())
        .build();

      Source bundleSource = Source.newBuilder("js", new File("/home/opc/streaming-setup/livestreams/bare-se/src/main/resources/beer.bundle.js")).build();

      ctx.eval(bundleSource);

      Source source = Source.newBuilder("js",
        "const beerMapBuilder = require('/print_map_module.js');" +
          "function createBeerMap(data) {" +
          "    return beerMapBuilder.getMap(JSON.parse(data)); " +
          "};", "code.js")
        .build();

      ctx.eval(source);
      return ctx.getBindings("js").getMember("createBeerMap");
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


      var client = WebClient.builder().baseUri("https://api.openbrewerydb.org/breweries/search?query=" + what).build();
      String result = client.get().request(String.class).toCompletableFuture().get();
      Value image = parseBeers.get().execute(result);

      response.headers().contentType(MediaType.TEXT_HTML);
      response.status(200);
      String html = "<!DOCTYPE html>" + "<html><body>" +
        "<h1>Breweries named '" +
        what +
        "' (or similar) in the US</h1>" +
        image +
        "</body></html>";
      response.send(html);


    } catch (Exception ignoreMe) {
      throw new RuntimeException(ignoreMe);
    }
  }
}
