// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/BooleanNegatedFunction.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import java.util.Objects;


/**
 * Implements the negation of the result of a boolean function.
 *
 * This class is {@link java.io.Serializable} to allow its subclass (also named Serializable) to be serialized, but
 * should not be regarded as "safely serializable" since the function it wraps is not itself guaranteed to be
 * serializable.
 */
class BooleanNegatedFunction8<A, B, C, D, E, F, G, H> implements BooleanFunction8<A, B, C, D, E, F, G, H>,
    java.io.Serializable {
  private static final int CLASS_HASH = BooleanNegatedFunction8.class.hashCode();

  private final BooleanFunction8<A, B, C, D, E, F, G, H> _function;

  // no-arg constructor for Kryo
  private BooleanNegatedFunction8() {
    _function = null;
  }

  /**
   * Creates a new instance that will negate the result provided by the given, wrapped function.
   *
   * @param function the function to be wrapped
   */
  private BooleanNegatedFunction8(BooleanFunction8<A, B, C, D, E, F, G, H> function) {
    _function = Objects.requireNonNull(function);
  }

  /**
   * Returns a function that will have a result that is a negation of the function provided.  If the passed function
   * is itself of this type, its underlying (wrapped) function will be returned; otherwise, a new instance of this class
   * will be created to wrap the passed function.
   *
   * @param function the function whose result will be negated
   * @return a function (which may or may not be new) that negates the return value of the provided function
   */
  static <A, B, C, D, E, F, G, H> BooleanFunction8<A, B, C, D, E, F, G, H> negate(
      BooleanFunction8<A, B, C, D, E, F, G, H> function) {
    if (function instanceof BooleanNegatedFunction8) {
      return ((BooleanNegatedFunction8<A, B, C, D, E, F, G, H>) function)._function; // negation of a negation is the original function
    }
    return new BooleanNegatedFunction8<>(function);
  }

  @Override
  public boolean apply(A value1, B value2, C value3, D value4, E value5, F value6, G value7, H value8) {
    return !_function.apply(value1, value2, value3, value4, value5, value6, value7, value8);
  }

  @Override
  public int hashCode() {
    return CLASS_HASH + _function.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof BooleanNegatedFunction8) {
      return this._function.equals(((BooleanNegatedFunction8) obj)._function);
    }
    return false;
  }

  static class Serializable<A, B, C, D, E, F, G, H> extends BooleanNegatedFunction8<A, B, C, D, E, F, G, H> implements
      BooleanFunction8.Serializable<A, B, C, D, E, F, G, H> {
    private static final long serialVersionUID = 1;

    // no-arg constructor for Kryo
    private Serializable() {
    }

    /**
     * Creates a new instance that will negate the result provided by the given, wrapped function.
     *
     * @param function the function to be wrapped
     */
    private Serializable(BooleanFunction8.Serializable<A, B, C, D, E, F, G, H> function) {
      super(function);
    }

    /**
     * Returns a function that will have a result that is a negation of the function provided.  If the passed function
     * is itself of this type, its underlying (wrapped) function will be returned; otherwise, a new instance of this class
     * will be created to wrap the passed function.
     *
     * @param function the function whose result will be negated
     * @return a function (which may or may not be new) that negates the return value of the provided function
     */
    static <A, B, C, D, E, F, G, H> BooleanFunction8.Serializable<A, B, C, D, E, F, G, H> negate(
        BooleanFunction8.Serializable<A, B, C, D, E, F, G, H> function) {
      if (function instanceof BooleanNegatedFunction8.Serializable) {
        // negation of a negation is the original function
        return (BooleanFunction8.Serializable<A, B, C, D, E, F, G, H>) ((BooleanNegatedFunction8<A, B, C, D, E, F, G, H>) function)._function;
      }
      return new BooleanNegatedFunction8.Serializable<>(function);
    }
  }
}
