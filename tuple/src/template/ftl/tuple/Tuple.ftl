<#import "common.ftl" as t />
<@t.AutoGeneratedWarning />
package com.linkedin.dagli.tuple;

import java.io.Serializable;
import java.util.Iterator;
import java.util.function.IntFunction;

/**
 * An ordered sequence of elements.
 */
public interface Tuple extends Serializable, Iterable<Object>, Comparable<Tuple> {
  /**
   * Gets an element from the tuple.
   *
   * @throws IndexOutOfBoundsException if index < 0 or index >= size()
   * @param index the index of the element to fetch
   * @return the element value
   */
  Object get(int index);

  /**
   * @return the size (arity) of the tuple
   */
  int size();

  @Override
  @SuppressWarnings("unchecked")
  default int compareTo(Tuple o) {
    int minSize = Math.min(this.size(), o.size());
    for (int i = 0; i < minSize; i++) {
      int comparison = AbstractTuple.ELEMENT_COMPARATOR.compare((Comparable) this.get(i), (Comparable) o.get(i));
      if (comparison != 0) {
        return comparison;
      }
    }

    // if all elements (up to the length of the shortest tuple) are the same, the longest is the largest
    return Integer.compare(this.size(), o.size());
  }

  /**
   * Returns the elements of this tuple in a new array.
   *
   * @return an array with the elements of this tuple
   */
  default Object[] toArray() {
    return toArray(Object[]::new);
  }

  /**
   * Returns the elements of this tuple in an array generated by arrayGenerator.
   *
   * @param arrayGenerator a method that will generate an array of the passed size in which to place the elements
   * @return an array with the elements of this tuple
   */
  @SuppressWarnings("unchecked")
  default <TT> TT[] toArray(IntFunction<TT[]> arrayGenerator) {
    TT[] array = arrayGenerator.apply(size());
    for (int i = 0; i < size(); i++) {
      array[i] = (TT) get(i);
    }
    return array;
  }

  @Override
  default Iterator<Object> iterator() {
    return new Iterator<Object>() {
      private int _offset = 0;

      @Override
      public boolean hasNext() {
        return _offset < size();
      }

      @Override
      public Object next() {
        return get(_offset++);
      }
    };
  }

  <#list 1..t.maxTupleSize as arity>
  /**
   * Creates a new tuple that contains the given elements.
   *
   * @return a new tuple with the provided elements
   */
  static <<@t.TypeParameters arity />> <@t.Tuple arity /> of(<#list 0..<arity as index>${t.typeParameters[index]} element${index}<#sep>, </#list>) {
    return Tuple${arity}.of(<#list 0..<arity as index>element${index}<#sep>, </#list>);
  }
  </#list>

  /**
   * Returns a TupleGenerator for the specified tuple size.  Size must be between 1 and the largest defined tuple size.
   *
   * @param size the size of tuple the returned TupleGenerator will generate
   * @throws ArrayIndexOutOfBoundsException if size <= 0 or size exceeds the largest defined tuple size.
   * @return a TupleGenerator for the specified tuple size.
   */
  static TupleGenerator generator(int size) {
    return TupleGenerators.GENERATORS[size - 1];
  }
}
