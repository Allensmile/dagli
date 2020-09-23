// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/DefaultOnNullArgument.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import java.util.Objects;


/**
 * A function class implementing ShortFunction2.Serializable<A, B> that returns 0 if any of
 * the function's inputs are null.
 */
class ShortDefaultOnNullArgument2<A, B> implements ShortFunction2.Serializable<A, B> {
  private static final long serialVersionUID = 1;
  private static final int CLASS_HASH = ShortDefaultOnNullArgument2.class.hashCode();
  private final ShortFunction2<A, B> _wrapped;

  ShortDefaultOnNullArgument2(ShortFunction2<A, B> wrapped) {
    // stacking this wrapper multiple times should be idempotent:
    if (wrapped instanceof ShortDefaultOnNullArgument2) {
      _wrapped = ((ShortDefaultOnNullArgument2) wrapped)._wrapped;
    } else {
      _wrapped = Objects.requireNonNull(wrapped);
    }
  }

  @Override
  public ShortDefaultOnNullArgument2<A, B> safelySerializable() {
    return new ShortDefaultOnNullArgument2<>(((ShortFunction2.Serializable<A, B>) _wrapped).safelySerializable());
  }

  @Override
  public short apply(A value1, B value2) {
    if (value1 == null || value2 == null) {
      return 0;
    }
    return _wrapped.apply(value1, value2);
  }

  @Override
  public int hashCode() {
    return CLASS_HASH + _wrapped.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ShortDefaultOnNullArgument2) {
      return this._wrapped.equals(((ShortDefaultOnNullArgument2) obj)._wrapped);
    }
    return false;
  }
}
