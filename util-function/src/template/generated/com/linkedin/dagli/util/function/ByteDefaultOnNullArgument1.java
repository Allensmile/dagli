// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/DefaultOnNullArgument.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import java.util.Objects;


/**
 * A function class implementing ByteFunction1.Serializable<A> that returns 0 if any of
 * the function's inputs are null.
 */
class ByteDefaultOnNullArgument1<A> implements ByteFunction1.Serializable<A> {
  private static final long serialVersionUID = 1;
  private static final int CLASS_HASH = ByteDefaultOnNullArgument1.class.hashCode();
  private final ByteFunction1<A> _wrapped;

  ByteDefaultOnNullArgument1(ByteFunction1<A> wrapped) {
    // stacking this wrapper multiple times should be idempotent:
    if (wrapped instanceof ByteDefaultOnNullArgument1) {
      _wrapped = ((ByteDefaultOnNullArgument1) wrapped)._wrapped;
    } else {
      _wrapped = Objects.requireNonNull(wrapped);
    }
  }

  @Override
  public ByteDefaultOnNullArgument1<A> safelySerializable() {
    return new ByteDefaultOnNullArgument1<>(((ByteFunction1.Serializable<A>) _wrapped).safelySerializable());
  }

  @Override
  public byte apply(A value1) {
    if (value1 == null) {
      return 0;
    }
    return _wrapped.apply(value1);
  }

  @Override
  public int hashCode() {
    return CLASS_HASH + _wrapped.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ByteDefaultOnNullArgument1) {
      return this._wrapped.equals(((ByteDefaultOnNullArgument1) obj)._wrapped);
    }
    return false;
  }
}
