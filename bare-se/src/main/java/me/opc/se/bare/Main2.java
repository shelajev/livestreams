package me.opc.se.bare;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.util.function.Consumer;
import java.util.function.Function;

public class Main2 {
  public static void main(String[] args) {
    Context ctx = Context.newBuilder("js").allowAllAccess(true).build();

    var x = (Function<Integer,Thenable>) (v)-> (Thenable) (onResolve, onReject) -> {
      onResolve.executeVoid(v + 42);
    };

    ctx.getBindings("js").putMember("javaAsync", x);
    var promise = ctx.eval("js", "(async () => {" +
      "   var v1 = await javaAsync(1);" +
      "   var v2 = await javaAsync(2);" +
      "   var v3 = await javaAsync(3);" +
      "   return v1 + v2 + v3;" +
      "})");
    var p = promise.execute().invokeMember("then", (Consumer<Object>)(v) -> System.out.println("Result " + v))
      .invokeMember("catch", (Consumer<Object>) System.out::println);
  }
}
