// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/ComposedFunction.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import java.util.Objects;


/**
 * A function class implementing {@link DoubleFunction7.Serializable} that composes
 * {@link DoubleFunction7} with a {@link Function1}.  The function is only <strong>actually</strong> serializable
 * if its constituent composed functions are serializable, of course.
 */
class DoubleComposedFunction7<A, B, C, D, E, F, G, Q> implements DoubleFunction7.Serializable<A, B, C, D, E, F, G> {
  private static final long serialVersionUID = 1;

  private final Function7<A, B, C, D, E, F, G, Q> _first;
  private final DoubleFunction1<? super Q> _andThen;

  /**
   * Creates a new instance that composes two functions, i.e. {@code andThen(first(inputs))}.
   *
   * @param first the first function to be called in the composition
   * @param andThen the second function to be called in the composition, accepting the {@code first} functions result
   *                as input
   */
  DoubleComposedFunction7(Function7<A, B, C, D, E, F, G, Q> first, DoubleFunction1<? super Q> andThen) {
    _first = first;
    _andThen = andThen;
  }

  @Override
  @SuppressWarnings("unchecked")
  public DoubleComposedFunction7<A, B, C, D, E, F, G, Q> safelySerializable() {
    return new DoubleComposedFunction7<>(
        ((Function7.Serializable<A, B, C, D, E, F, G, Q>) _first).safelySerializable(),
        ((DoubleFunction1.Serializable<? super Q>) _andThen).safelySerializable());
  }

  @Override
  public double apply(A value1, B value2, C value3, D value4, E value5, F value6, G value7) {
    return _andThen.apply(_first.apply(value1, value2, value3, value4, value5, value6, value7));
  }

  @Override
  public int hashCode() {
    return Objects.hash(DoubleComposedFunction7.class, _first, _andThen);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DoubleComposedFunction7) {
      return this._first.equals(((DoubleComposedFunction7) obj)._first)
          && this._andThen.equals(((DoubleComposedFunction7) obj)._andThen);
    }
    return false;
  }
}
