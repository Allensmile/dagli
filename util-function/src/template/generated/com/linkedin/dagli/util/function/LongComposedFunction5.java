// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/ComposedFunction.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import java.util.Objects;


/**
 * A function class implementing {@link LongFunction5.Serializable} that composes
 * {@link LongFunction5} with a {@link Function1}.  The function is only <strong>actually</strong> serializable
 * if its constituent composed functions are serializable, of course.
 */
class LongComposedFunction5<A, B, C, D, E, Q> implements LongFunction5.Serializable<A, B, C, D, E> {
  private static final long serialVersionUID = 1;

  private final Function5<A, B, C, D, E, Q> _first;
  private final LongFunction1<? super Q> _andThen;

  /**
   * Creates a new instance that composes two functions, i.e. {@code andThen(first(inputs))}.
   *
   * @param first the first function to be called in the composition
   * @param andThen the second function to be called in the composition, accepting the {@code first} functions result
   *                as input
   */
  LongComposedFunction5(Function5<A, B, C, D, E, Q> first, LongFunction1<? super Q> andThen) {
    _first = first;
    _andThen = andThen;
  }

  @Override
  @SuppressWarnings("unchecked")
  public LongComposedFunction5<A, B, C, D, E, Q> safelySerializable() {
    return new LongComposedFunction5<>(((Function5.Serializable<A, B, C, D, E, Q>) _first).safelySerializable(),
        ((LongFunction1.Serializable<? super Q>) _andThen).safelySerializable());
  }

  @Override
  public long apply(A value1, B value2, C value3, D value4, E value5) {
    return _andThen.apply(_first.apply(value1, value2, value3, value4, value5));
  }

  @Override
  public int hashCode() {
    return Objects.hash(LongComposedFunction5.class, _first, _andThen);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof LongComposedFunction5) {
      return this._first.equals(((LongComposedFunction5) obj)._first)
          && this._andThen.equals(((LongComposedFunction5) obj)._andThen);
    }
    return false;
  }
}
