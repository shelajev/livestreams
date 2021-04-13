package me.opc.se.bare;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.util.function.Consumer;

public class Main2 {
  public static void main(String[] args) {
    Context ctx = Context.create("js");

    var x = ctx.eval("js", "(async function foo () { return 42; })");


    Value then = x.invokeMember("then",
      (Consumer<Object>) (inside -> System.out.println("Java +" + inside)));

  }
}