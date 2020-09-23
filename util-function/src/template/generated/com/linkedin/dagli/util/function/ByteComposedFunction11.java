// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/ComposedFunction.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import java.util.Objects;


/**
 * A function class implementing {@link ByteFunction11.Serializable} that composes
 * {@link ByteFunction11} with a {@link Function1}.  The function is only <strong>actually</strong> serializable
 * if its constituent composed functions are serializable, of course.
 */
class ByteComposedFunction11<A, B, C, D, E, F, G, H, I, J, K, Q> implements
    ByteFunction11.Serializable<A, B, C, D, E, F, G, H, I, J, K> {
  private static final long serialVersionUID = 1;

  private final Function11<A, B, C, D, E, F, G, H, I, J, K, Q> _first;
  private final ByteFunction1<? super Q> _andThen;

  /**
   * Creates a new instance that composes two functions, i.e. {@code andThen(first(inputs))}.
   *
   * @param first the first function to be called in the composition
   * @param andThen the second function to be called in the composition, accepting the {@code first} functions result
   *                as input
   */
  ByteComposedFunction11(Function11<A, B, C, D, E, F, G, H, I, J, K, Q> first, ByteFunction1<? super Q> andThen) {
    _first = first;
    _andThen = andThen;
  }

  @Override
  @SuppressWarnings("unchecked")
  public ByteComposedFunction11<A, B, C, D, E, F, G, H, I, J, K, Q> safelySerializable() {
    return new ByteComposedFunction11<>(
        ((Function11.Serializable<A, B, C, D, E, F, G, H, I, J, K, Q>) _first).safelySerializable(),
        ((ByteFunction1.Serializable<? super Q>) _andThen).safelySerializable());
  }

  @Override
  public byte apply(A value1, B value2, C value3, D value4, E value5, F value6, G value7, H value8, I value9,
      J value10, K value11) {
    return _andThen.apply(_first.apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10,
        value11));
  }

  @Override
  public int hashCode() {
    return Objects.hash(ByteComposedFunction11.class, _first, _andThen);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ByteComposedFunction11) {
      return this._first.equals(((ByteComposedFunction11) obj)._first)
          && this._andThen.equals(((ByteComposedFunction11) obj)._andThen);
    }
    return false;
  }
}
