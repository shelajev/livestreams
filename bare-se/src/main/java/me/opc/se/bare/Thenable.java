package me.opc.se.bare;

import org.graalvm.polyglot.Value;

@FunctionalInterface
public interface Thenable {
  void then(Value resolve, Value reject);
}
