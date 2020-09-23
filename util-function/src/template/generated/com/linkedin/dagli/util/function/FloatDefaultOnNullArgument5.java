// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/DefaultOnNullArgument.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import java.util.Objects;


/**
 * A function class implementing FloatFunction5.Serializable<A, B, C, D, E> that returns 0 if any of
 * the function's inputs are null.
 */
class FloatDefaultOnNullArgument5<A, B, C, D, E> implements FloatFunction5.Serializable<A, B, C, D, E> {
  private static final long serialVersionUID = 1;
  private static final int CLASS_HASH = FloatDefaultOnNullArgument5.class.hashCode();
  private final FloatFunction5<A, B, C, D, E> _wrapped;

  FloatDefaultOnNullArgument5(FloatFunction5<A, B, C, D, E> wrapped) {
    // stacking this wrapper multiple times should be idempotent:
    if (wrapped instanceof FloatDefaultOnNullArgument5) {
      _wrapped = ((FloatDefaultOnNullArgument5) wrapped)._wrapped;
    } else {
      _wrapped = Objects.requireNonNull(wrapped);
    }
  }

  @Override
  public FloatDefaultOnNullArgument5<A, B, C, D, E> safelySerializable() {
    return new FloatDefaultOnNullArgument5<>(
        ((FloatFunction5.Serializable<A, B, C, D, E>) _wrapped).safelySerializable());
  }

  @Override
  public float apply(A value1, B value2, C value3, D value4, E value5) {
    if (value1 == null || value2 == null || value3 == null || value4 == null || value5 == null) {
      return 0;
    }
    return _wrapped.apply(value1, value2, value3, value4, value5);
  }

  @Override
  public int hashCode() {
    return CLASS_HASH + _wrapped.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof FloatDefaultOnNullArgument5) {
      return this._wrapped.equals(((FloatDefaultOnNullArgument5) obj)._wrapped);
    }
    return false;
  }
}
