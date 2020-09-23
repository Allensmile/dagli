// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/ComposedFunction.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import java.util.Objects;


/**
 * A function class implementing {@link ByteFunction2.Serializable} that composes
 * {@link ByteFunction2} with a {@link Function1}.  The function is only <strong>actually</strong> serializable
 * if its constituent composed functions are serializable, of course.
 */
class ByteComposedFunction2<A, B, Q> implements ByteFunction2.Serializable<A, B> {
  private static final long serialVersionUID = 1;

  private final Function2<A, B, Q> _first;
  private final ByteFunction1<? super Q> _andThen;

  /**
   * Creates a new instance that composes two functions, i.e. {@code andThen(first(inputs))}.
   *
   * @param first the first function to be called in the composition
   * @param andThen the second function to be called in the composition, accepting the {@code first} functions result
   *                as input
   */
  ByteComposedFunction2(Function2<A, B, Q> first, ByteFunction1<? super Q> andThen) {
    _first = first;
    _andThen = andThen;
  }

  @Override
  @SuppressWarnings("unchecked")
  public ByteComposedFunction2<A, B, Q> safelySerializable() {
    return new ByteComposedFunction2<>(((Function2.Serializable<A, B, Q>) _first).safelySerializable(),
        ((ByteFunction1.Serializable<? super Q>) _andThen).safelySerializable());
  }

  @Override
  public byte apply(A value1, B value2) {
    return _andThen.apply(_first.apply(value1, value2));
  }

  @Override
  public int hashCode() {
    return Objects.hash(ByteComposedFunction2.class, _first, _andThen);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ByteComposedFunction2) {
      return this._first.equals(((ByteComposedFunction2) obj)._first)
          && this._andThen.equals(((ByteComposedFunction2) obj)._andThen);
    }
    return false;
  }
}
