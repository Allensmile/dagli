// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/DefaultOnNullArgument.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import java.util.Objects;


/**
 * A function class implementing DoubleFunction7.Serializable<A, B, C, D, E, F, G> that returns 0 if any of
 * the function's inputs are null.
 */
class DoubleDefaultOnNullArgument7<A, B, C, D, E, F, G> implements DoubleFunction7.Serializable<A, B, C, D, E, F, G> {
  private static final long serialVersionUID = 1;
  private static final int CLASS_HASH = DoubleDefaultOnNullArgument7.class.hashCode();
  private final DoubleFunction7<A, B, C, D, E, F, G> _wrapped;

  DoubleDefaultOnNullArgument7(DoubleFunction7<A, B, C, D, E, F, G> wrapped) {
    // stacking this wrapper multiple times should be idempotent:
    if (wrapped instanceof DoubleDefaultOnNullArgument7) {
      _wrapped = ((DoubleDefaultOnNullArgument7) wrapped)._wrapped;
    } else {
      _wrapped = Objects.requireNonNull(wrapped);
    }
  }

  @Override
  public DoubleDefaultOnNullArgument7<A, B, C, D, E, F, G> safelySerializable() {
    return new DoubleDefaultOnNullArgument7<>(
        ((DoubleFunction7.Serializable<A, B, C, D, E, F, G>) _wrapped).safelySerializable());
  }

  @Override
  public double apply(A value1, B value2, C value3, D value4, E value5, F value6, G value7) {
    if (value1 == null || value2 == null || value3 == null || value4 == null || value5 == null || value6 == null
        || value7 == null) {
      return 0;
    }
    return _wrapped.apply(value1, value2, value3, value4, value5, value6, value7);
  }

  @Override
  public int hashCode() {
    return CLASS_HASH + _wrapped.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DoubleDefaultOnNullArgument7) {
      return this._wrapped.equals(((DoubleDefaultOnNullArgument7) obj)._wrapped);
    }
    return false;
  }
}
