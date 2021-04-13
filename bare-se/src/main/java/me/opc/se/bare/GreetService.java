
package me.opc.se.bare;

import java.util.Collections;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import io.helidon.config.Config;
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

    private final String greeting;

    GreetService(Config config) {
        greeting = config.get("app.greeting").asString().orElse("Ciao");
    }

    /**
     * A service registers itself by updating the routing rules.
     * @param rules the routing rules.
     */
    @Override
    public void update(Routing.Rules rules) {
        rules.get("/{what}", this::getDefaultMessageHandler);
    }

    /**
     * Return a worldly greeting message.
     * @param request the server request
     * @param response the server response
     */
    private void getDefaultMessageHandler(ServerRequest request, ServerResponse response) {
        var what = request.path().param("what");


        Context ctx = Context.newBuilder("js")
          .allowAllAccess(true)
          .build();

        WebCL

        ctx.eval("js", "function add(a, b) { return a + b; }");

        var eval = ctx.getBindings("js").getMember("add");

        String msg = String.format("%s %s!", greeting, eval.execute(what, what));
        LOGGER.info("Greeting message is " + msg);
        JsonObject returnObject = JSON.createObjectBuilder()
                .add("message", msg)
                .build();
        response.send(returnObject);
    }
}