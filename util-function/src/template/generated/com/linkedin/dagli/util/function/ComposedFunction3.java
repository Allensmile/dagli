// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/ComposedFunction.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import java.util.Objects;


/**
 * A function class implementing {@link Function3.Serializable} that composes
 * {@link Function3} with a {@link Function1}.  The function is only <strong>actually</strong> serializable
 * if its constituent composed functions are serializable, of course.
 */
class ComposedFunction3<A, B, C, R, Q> implements Function3.Serializable<A, B, C, R> {
  private static final long serialVersionUID = 1;

  private final Function3<A, B, C, Q> _first;
  private final Function1<? super Q, ? extends R> _andThen;

  /**
   * Creates a new instance that composes two functions, i.e. {@code andThen(first(inputs))}.
   *
   * @param first the first function to be called in the composition
   * @param andThen the second function to be called in the composition, accepting the {@code first} functions result
   *                as input
   */
  ComposedFunction3(Function3<A, B, C, Q> first, Function1<? super Q, ? extends R> andThen) {
    _first = first;
    _andThen = andThen;
  }

  @Override
  @SuppressWarnings("unchecked")
  public ComposedFunction3<A, B, C, R, Q> safelySerializable() {
    return new ComposedFunction3<>(((Function3.Serializable<A, B, C, Q>) _first).safelySerializable(),
        ((Function1.Serializable<? super Q, ? extends R>) _andThen).safelySerializable());
  }

  @Override
  public R apply(A value1, B value2, C value3) {
    return _andThen.apply(_first.apply(value1, value2, value3));
  }

  @Override
  public int hashCode() {
    return Objects.hash(ComposedFunction3.class, _first, _andThen);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ComposedFunction3) {
      return this._first.equals(((ComposedFunction3) obj)._first)
          && this._andThen.equals(((ComposedFunction3) obj)._andThen);
    }
    return false;
  }
}
