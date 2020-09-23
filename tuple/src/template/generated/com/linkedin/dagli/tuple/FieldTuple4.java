// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the tuple/FieldTupleX.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.tuple;

/**
 * A tuple of arity ArrayTuple4<A, B, C, D>.
 * FieldTuples are more memory-efficient than ArrayTuples because they do not need to store an extra object (the array),
 * but ArrayTuples are faster to create if the tuple elements are already extant within an array.
 */
final class FieldTuple4<A, B, C, D> extends AbstractTuple implements Tuple4<A, B, C, D> {
  private static final long serialVersionUID = 1;

  private final A _element0;
  private final B _element1;
  private final C _element2;
  private final D _element3;

  /**
   * Creates a new tuple from the given fields.
   */
  public FieldTuple4(A element0, B element1, C element2, D element3) {
    _element0 = element0;
    _element1 = element1;
    _element2 = element2;
    _element3 = element3;
  }

  @Override
  public A get0() {
    return _element0;
  }

  @Override
  public B get1() {
    return _element1;
  }

  @Override
  public C get2() {
    return _element2;
  }

  @Override
  public D get3() {
    return _element3;
  }

  @Override
  public Object get(int index) {
    switch (index) {
      case 0:
        return _element0;
      case 1:
        return _element1;
      case 2:
        return _element2;
      case 3:
        return _element3;
      default:
        throw new IndexOutOfBoundsException();
    }
  }

  @Override
  public int size() {
    return 4;
  }
}
