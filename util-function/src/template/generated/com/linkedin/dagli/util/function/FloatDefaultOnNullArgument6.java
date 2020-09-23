// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/DefaultOnNullArgument.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import java.util.Objects;


/**
 * A function class implementing FloatFunction6.Serializable<A, B, C, D, E, F> that returns 0 if any of
 * the function's inputs are null.
 */
class FloatDefaultOnNullArgument6<A, B, C, D, E, F> implements FloatFunction6.Serializable<A, B, C, D, E, F> {
  private static final long serialVersionUID = 1;
  private static final int CLASS_HASH = FloatDefaultOnNullArgument6.class.hashCode();
  private final FloatFunction6<A, B, C, D, E, F> _wrapped;

  FloatDefaultOnNullArgument6(FloatFunction6<A, B, C, D, E, F> wrapped) {
    // stacking this wrapper multiple times should be idempotent:
    if (wrapped instanceof FloatDefaultOnNullArgument6) {
      _wrapped = ((FloatDefaultOnNullArgument6) wrapped)._wrapped;
    } else {
      _wrapped = Objects.requireNonNull(wrapped);
    }
  }

  @Override
  public FloatDefaultOnNullArgument6<A, B, C, D, E, F> safelySerializable() {
    return new FloatDefaultOnNullArgument6<>(
        ((FloatFunction6.Serializable<A, B, C, D, E, F>) _wrapped).safelySerializable());
  }

  @Override
  public float apply(A value1, B value2, C value3, D value4, E value5, F value6) {
    if (value1 == null || value2 == null || value3 == null || value4 == null || value5 == null || value6 == null) {
      return 0;
    }
    return _wrapped.apply(value1, value2, value3, value4, value5, value6);
  }

  @Override
  public int hashCode() {
    return CLASS_HASH + _wrapped.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof FloatDefaultOnNullArgument6) {
      return this._wrapped.equals(((FloatDefaultOnNullArgument6) obj)._wrapped);
    }
    return false;
  }
}
