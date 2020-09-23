// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the util/function/MethodReference.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.util.function;

import java.io.IOException;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;


/**
 * Represents a safely serializable method reference (assuming the JVM supports {@link MethodReference}, which is not
 * guaranteed).  If the JVM does not support {@link MethodReference}, an exception may be thrown when creating the
 * method reference; fortunately, however, deserialization will not be affected: if you can serialize it, you'll be able
 * to deserialize later on any JVM.
 */
class BooleanMethodReference4<A, B, C, D> implements BooleanFunction4.Serializable<A, B, C, D> {
  private static final long serialVersionUID = 1;

  // hash and equality distinguish between different types of MethodReferenceX classes even when the underlying method
  // references are identical
  private static final int CLASS_HASH = BooleanMethodReference4.class.hashCode();

  private final MethodReference _methodReference;
  private transient BooleanFunction4<A, B, C, D> _cachedFunction = null;

  BooleanMethodReference4(MethodReference mr) {
    _methodReference = mr;
    initCachedFunction();
  }

  /**
   * Creates a new instance.
   *
   * The passed func parameter must be a method reference, such as Object::toString or String::length.  A runtime
   * exception will be thrown if func is a function object or an anonymous lambda (e.g. "a -> a + 5").  An exception
   * may also be thrown if your JVM implementation does not support the library's internal method for retrieving
   * information about the passed method reference.
   *
   * @param func a method reference lambda to wrap.  If this function is not safely serializable, a runtime exception
   *        will be thrown.
   */
  public BooleanMethodReference4(Serializable<A, B, C, D> func) {
    if (func instanceof BooleanMethodReference4) {
      // multiple applications of this constructor are idempotent:
      _methodReference = ((BooleanMethodReference4) func)._methodReference;
    } else {
      // this line will thrown an exception if func is not a method reference:
      _methodReference = new MethodReference(func);
    }
    initCachedFunction();
  }

  // deserialization hook to ensure _cachedFunction is always set
  private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    initCachedFunction();
  }

  private void initCachedFunction() {
    if (_cachedFunction == null) {
      _cachedFunction = fromMethodReference();
    }
  }

  private BooleanFunction4<A, B, C, D> fromMethodReference() {
    if (_methodReference.isBound()) {
      BooleanMethodReference5<Object, A, B, C, D> unbound = new BooleanMethodReference5<>(_methodReference.unbind());
      return (A value1, B value2, C value3, D value4) -> unbound.apply(_methodReference.getBoundInstance(), value1,
          value2, value3, value4);
    }

    MethodHandle mh = _methodReference.getMethodHandle();

    try {
      MethodType type = mh.type().generic().changeReturnType(boolean.class);
      return (BooleanFunction4<A, B, C, D>) LambdaMetafactory
          .metafactory(_methodReference.getLookup(), "apply", MethodType.methodType(BooleanFunction4.class), type, mh,
              mh.type().wrap().changeReturnType(boolean.class)).getTarget().invokeExact();
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  public boolean apply(A value1, B value2, C value3, D value4) {
    return _cachedFunction.apply(value1, value2, value3, value4);
  }

  @Override
  public int hashCode() {
    return CLASS_HASH + _methodReference.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof BooleanMethodReference4)) {
      return false;
    }

    return _methodReference.equals(((BooleanMethodReference4) obj)._methodReference);
  }

  @Override
  public String toString() {
    return _methodReference.toString();
  }
}
