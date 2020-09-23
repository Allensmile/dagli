// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the tuple/TupleX.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.tuple;

import java.util.Iterator;


/**
 * An ordered sequence of 17 elements.
 */
public interface Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> extends Tuple, TupleValue0<A>,
    TupleValue1<B>, TupleValue2<C>, TupleValue3<D>, TupleValue4<E>, TupleValue5<F>, TupleValue6<G>, TupleValue7<H>,
    TupleValue8<I>, TupleValue9<J>, TupleValue10<K>, TupleValue11<L>, TupleValue12<M>, TupleValue13<N>,
    TupleValue14<O>, TupleValue15<P>, TupleValue16<Q> {
  /**
   * Gets element 0 from the tuple
   *
   * @return the element value
   */
  @Override
  A get0();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 0, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 0 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 0 replaced with a new value
   */
  default <Z> Tuple17<Z, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> withValue0(Z value) {
    return of(value, get1(), get2(), get3(), get4(), get5(), get6(), get7(), get8(), get9(), get10(), get11(), get12(),
        get13(), get14(), get15(), get16());
  }

  /**
   * Gets element 1 from the tuple
   *
   * @return the element value
   */
  @Override
  B get1();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 1, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 1 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 1 replaced with a new value
   */
  default <Z> Tuple17<A, Z, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> withValue1(Z value) {
    return of(get0(), value, get2(), get3(), get4(), get5(), get6(), get7(), get8(), get9(), get10(), get11(), get12(),
        get13(), get14(), get15(), get16());
  }

  /**
   * Gets element 2 from the tuple
   *
   * @return the element value
   */
  @Override
  C get2();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 2, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 2 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 2 replaced with a new value
   */
  default <Z> Tuple17<A, B, Z, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> withValue2(Z value) {
    return of(get0(), get1(), value, get3(), get4(), get5(), get6(), get7(), get8(), get9(), get10(), get11(), get12(),
        get13(), get14(), get15(), get16());
  }

  /**
   * Gets element 3 from the tuple
   *
   * @return the element value
   */
  @Override
  D get3();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 3, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 3 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 3 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, Z, E, F, G, H, I, J, K, L, M, N, O, P, Q> withValue3(Z value) {
    return of(get0(), get1(), get2(), value, get4(), get5(), get6(), get7(), get8(), get9(), get10(), get11(), get12(),
        get13(), get14(), get15(), get16());
  }

  /**
   * Gets element 4 from the tuple
   *
   * @return the element value
   */
  @Override
  E get4();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 4, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 4 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 4 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, D, Z, F, G, H, I, J, K, L, M, N, O, P, Q> withValue4(Z value) {
    return of(get0(), get1(), get2(), get3(), value, get5(), get6(), get7(), get8(), get9(), get10(), get11(), get12(),
        get13(), get14(), get15(), get16());
  }

  /**
   * Gets element 5 from the tuple
   *
   * @return the element value
   */
  @Override
  F get5();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 5, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 5 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 5 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, D, E, Z, G, H, I, J, K, L, M, N, O, P, Q> withValue5(Z value) {
    return of(get0(), get1(), get2(), get3(), get4(), value, get6(), get7(), get8(), get9(), get10(), get11(), get12(),
        get13(), get14(), get15(), get16());
  }

  /**
   * Gets element 6 from the tuple
   *
   * @return the element value
   */
  @Override
  G get6();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 6, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 6 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 6 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, D, E, F, Z, H, I, J, K, L, M, N, O, P, Q> withValue6(Z value) {
    return of(get0(), get1(), get2(), get3(), get4(), get5(), value, get7(), get8(), get9(), get10(), get11(), get12(),
        get13(), get14(), get15(), get16());
  }

  /**
   * Gets element 7 from the tuple
   *
   * @return the element value
   */
  @Override
  H get7();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 7, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 7 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 7 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, D, E, F, G, Z, I, J, K, L, M, N, O, P, Q> withValue7(Z value) {
    return of(get0(), get1(), get2(), get3(), get4(), get5(), get6(), value, get8(), get9(), get10(), get11(), get12(),
        get13(), get14(), get15(), get16());
  }

  /**
   * Gets element 8 from the tuple
   *
   * @return the element value
   */
  @Override
  I get8();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 8, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 8 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 8 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, D, E, F, G, H, Z, J, K, L, M, N, O, P, Q> withValue8(Z value) {
    return of(get0(), get1(), get2(), get3(), get4(), get5(), get6(), get7(), value, get9(), get10(), get11(), get12(),
        get13(), get14(), get15(), get16());
  }

  /**
   * Gets element 9 from the tuple
   *
   * @return the element value
   */
  @Override
  J get9();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 9, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 9 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 9 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, D, E, F, G, H, I, Z, K, L, M, N, O, P, Q> withValue9(Z value) {
    return of(get0(), get1(), get2(), get3(), get4(), get5(), get6(), get7(), get8(), value, get10(), get11(), get12(),
        get13(), get14(), get15(), get16());
  }

  /**
   * Gets element 10 from the tuple
   *
   * @return the element value
   */
  @Override
  K get10();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 10, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 10 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 10 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, D, E, F, G, H, I, J, Z, L, M, N, O, P, Q> withValue10(Z value) {
    return of(get0(), get1(), get2(), get3(), get4(), get5(), get6(), get7(), get8(), get9(), value, get11(), get12(),
        get13(), get14(), get15(), get16());
  }

  /**
   * Gets element 11 from the tuple
   *
   * @return the element value
   */
  @Override
  L get11();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 11, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 11 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 11 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, D, E, F, G, H, I, J, K, Z, M, N, O, P, Q> withValue11(Z value) {
    return of(get0(), get1(), get2(), get3(), get4(), get5(), get6(), get7(), get8(), get9(), get10(), value, get12(),
        get13(), get14(), get15(), get16());
  }

  /**
   * Gets element 12 from the tuple
   *
   * @return the element value
   */
  @Override
  M get12();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 12, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 12 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 12 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, Z, N, O, P, Q> withValue12(Z value) {
    return of(get0(), get1(), get2(), get3(), get4(), get5(), get6(), get7(), get8(), get9(), get10(), get11(), value,
        get13(), get14(), get15(), get16());
  }

  /**
   * Gets element 13 from the tuple
   *
   * @return the element value
   */
  @Override
  N get13();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 13, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 13 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 13 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, Z, O, P, Q> withValue13(Z value) {
    return of(get0(), get1(), get2(), get3(), get4(), get5(), get6(), get7(), get8(), get9(), get10(), get11(),
        get12(), value, get14(), get15(), get16());
  }

  /**
   * Gets element 14 from the tuple
   *
   * @return the element value
   */
  @Override
  O get14();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 14, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 14 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 14 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, Z, P, Q> withValue14(Z value) {
    return of(get0(), get1(), get2(), get3(), get4(), get5(), get6(), get7(), get8(), get9(), get10(), get11(),
        get12(), get13(), value, get15(), get16());
  }

  /**
   * Gets element 15 from the tuple
   *
   * @return the element value
   */
  @Override
  P get15();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 15, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 15 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 15 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, Z, Q> withValue15(Z value) {
    return of(get0(), get1(), get2(), get3(), get4(), get5(), get6(), get7(), get8(), get9(), get10(), get11(),
        get12(), get13(), get14(), value, get16());
  }

  /**
   * Gets element 16 from the tuple
   *
   * @return the element value
   */
  @Override
  Q get16();

  /**
   * Gets a new tuple containing the same elements as this tuple except for element 16, which will be set to the
    provided value.
   *
   * @param value the value that should replace element 16 in the new tuple
   * @return a new tuple with the same elements as this one, but with element 16 replaced with a new value
   */
  default <Z> Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Z> withValue16(Z value) {
    return of(get0(), get1(), get2(), get3(), get4(), get5(), get6(), get7(), get8(), get9(), get10(), get11(),
        get12(), get13(), get14(), get15(), value);
  }

  /**
   * @return 17
   */
  @Override
  default int size() {
    return 17;
  }

  /**
   * Creates a new tuple that contains the given elements.
   *
   * @return a new tuple with the provided elements
   */
  static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> of(
      A element0, B element1, C element2, D element3, E element4, F element5, G element6, H element7, I element8,
      J element9, K element10, L element11, M element12, N element13, O element14, P element15, Q element16) {
    return new FieldTuple17<>(element0, element1, element2, element3, element4, element5, element6, element7, element8,
        element9, element10, element11, element12, element13, element14, element15, element16);
  }

  /**
   * Creates a new tuple that contains the given elements.  The tuple takes ownership of the provided array, which
   * should not be subsequently modified.  The array's length must be at least 17.
   *
   * This method is "unsafe" because the arrays values cannot be type-checked to ensure that they conform to their
   * purported types.
   *
   * @return a new tuple with the provided elements
   */
  static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> fromArrayUnsafe(
      Object[] elements) {
    return new ArrayTuple17<>(elements);
  }

  /**
   * Creates a new tuple containing the first 17 elements of the provided iterable.
   *
   * This method is "unsafe" because the iterated values cannot be type-checked to ensure that they conform to their
   * purported types.
   *
   * @param elements the iterable containing the elements to copy into a new tuple.
   * @throws java.util.NoSuchElementException if there are fewer than 17 elements in the provided iterable.
   * @return a new tuple with the provided elements
   */
  static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> fromIterableUnsafe(
      Iterable<?> elements) {
    Iterator<?> iterator = elements.iterator();
    if (iterator instanceof AutoCloseable) {
      try (AutoCloseable closeable = (AutoCloseable) iterator) {
        return fromIteratorUnsafe(iterator);
      } catch (RuntimeException e) {
        throw e; // rethrow unmodified
      } catch (Exception e) {
        // checked exceptions might be thrown when closing the iterator
        throw new RuntimeException(e);
      }
    }

    return fromIteratorUnsafe(elements.iterator());
  }

  /**
   * Creates a new tuple containing the first 17 elements of the provided iterator.
   *
   * This method is "unsafe" because the iterated values cannot be type-checked to ensure that they conform to their
   * purported types.
   *
   * @param elements the iterator containing the elements to copy into a new tuple.
   * @throws java.util.NoSuchElementException if there are fewer than 17 elements in the provided iterator.
   * @return a new tuple with the provided elements
   */
  @SuppressWarnings("unchecked")
  static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> Tuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> fromIteratorUnsafe(
      Iterator<?> elements) {
    return new FieldTuple17<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>((A) elements.next(),
        (B) elements.next(), (C) elements.next(), (D) elements.next(), (E) elements.next(), (F) elements.next(),
        (G) elements.next(), (H) elements.next(), (I) elements.next(), (J) elements.next(), (K) elements.next(),
        (L) elements.next(), (M) elements.next(), (N) elements.next(), (O) elements.next(), (P) elements.next(),
        (Q) elements.next());
  }
}
