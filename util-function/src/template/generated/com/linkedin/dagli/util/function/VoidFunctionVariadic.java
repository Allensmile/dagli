// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/VariadicFunction.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import com.linkedin.dagli.util.exception.Exceptions;


@FunctionalInterface
public interface VoidFunctionVariadic<A> extends FunctionBase {
  void apply(A... args);

  static <A> VoidFunctionVariadic<A> unchecked(Checked<A, ?> checkedFunction) {
    return (A... args) -> {
      try {
        checkedFunction.apply(args);
      } catch (Throwable e) {
        throw Exceptions.asRuntimeException(e);
      }
    };
  }

  @FunctionalInterface
  interface Checked<A, X extends Throwable> extends FunctionBase {
    void apply(A... args) throws X;
  }

  interface Serializable<A> extends VoidFunctionVariadic<A>, java.io.Serializable {
  }
}
