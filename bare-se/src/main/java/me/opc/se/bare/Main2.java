package me.opc.se.bare;

import org.graalvm.polyglot.Context;

import java.util.function.Consumer;

public class Main2 {
  public static void main(String[] args) {
    Context ctx = Context.create("js");

    var x = ctx.eval("js", "new Promise((resolve, reject) => {" +
      "resolve(42);" +
      "})");

    x.invokeMember("then",
      (Consumer<Object>) (inside -> System.out.println("Java +" + inside)));

  }
}
