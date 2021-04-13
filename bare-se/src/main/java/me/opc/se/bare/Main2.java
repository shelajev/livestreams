package me.opc.se.bare;

import org.graalvm.polyglot.Context;

public class Main2 {
  public static void main(String[] args) {
    Context ctx = Context.create("js");

    var x = ctx.eval("js", "new Promise((resolve, reject) => {" +
      "reject(42);" +
      "}).then(console.log).catch(console.log)");

  }
}
