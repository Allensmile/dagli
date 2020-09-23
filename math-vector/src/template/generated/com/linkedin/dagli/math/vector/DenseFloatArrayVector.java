// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the math/vector/DenseXArrayVector.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.math.vector;

import com.linkedin.dagli.util.array.ArraysEx;
import java.util.Arrays;
import java.util.Objects;


/**
 * Implementation of a {@link Vector} where the indices of non-zero values are all >= 0; these values are stored in
 * an array.
 */
public final class DenseFloatArrayVector extends AbstractVector implements MutableDenseVector, Cloneable {
  private static final long serialVersionUID = 1;

  private final float[] _values;

  /**
   * This constructor is primarily for Kryo deserialization, but it's used for empty vectors in general, too.
   */
  public DenseFloatArrayVector() {
    this(ArraysEx.EMPTY_FLOAT_ARRAY, true);
  }

  /**
   * Creates a {@link DenseFloatArrayVector} that will have a capacity for the provided number of elements.
   * Elements with indices <code>[0, capacity - 1]</code> will be settable to non-zero values.  Initially all elements
   * will have a value of 0.
   *
   * @param capacity the size of the underlying array for the vector
   */
  public DenseFloatArrayVector(int capacity) {
    this(new float[capacity], true);
  }

  /**
   * Creates a {@link DenseFloatArrayVector} that wraps the provided array of feature values.  The indices of these values in the
   * vector will match those in the array.
   *
   * <strong>Changes to the array will be reflected in this {@link DenseFloatArrayVector} (and vice-versa).</strong>
   *
   * @param values the array of feature values.  {@link DenseFloatArrayVector} will store a reference, not a copy, of this array.
   */
  public static DenseFloatArrayVector wrap(float... values) {
    return new DenseFloatArrayVector(values, true);
  }

  @Override
  public DenseFloatArrayVector clone() {
    return new DenseFloatArrayVector(_values); // constructor will make copy of the array
  }

  @Override
  public int hashCode() {
    int hash = 0;
    // use specialized implementation to expedite hashing
    for (int i = 0; i < _values.length; i++) {
      if (_values[i] != 0) {
        hash += VectorElement.hashCode(i, _values[i]);
      }
    }

    return hash;
  }

  @Override
  public boolean equals(Object other) {
    // check for reference equality
    if (this == other) {
      return true;
    }

    // efficiently handle the common case of comparing to same class of vector
    if (other instanceof DenseFloatArrayVector) {
      float[] otherValues = ((DenseFloatArrayVector) other)._values;
      if (otherValues.length == _values.length) {
        return Arrays.equals(otherValues, _values);
      } else {
        int bound = Math.min(otherValues.length, _values.length);
        if (!Arrays.equals(otherValues, 0, bound, _values, 0, bound)) {
          return false;
        }

        // since one array is longer than the other, iterate over remaining elements to make sure they're all 0:
        for (int i = bound; i < otherValues.length; i++) {
          if (otherValues[i] != 0) {
            return false;
          }
        }
        for (int i = bound; i < _values.length; i++) {
          if (_values[i] != 0) {
            return false;
          }
        }

        return true;
      }
    }

    return super.equals(other);
  }

  /**
   * Creates a {@link DenseFloatArrayVector} with the values of the provided Vector.  An exception will occur if any element index
   * is negative or exceeds Integer.MAX_VALUE.
   *
   * @param vector an existing {@link Vector} to copy.
   */
  public DenseFloatArrayVector(Vector vector) {
    this(vector instanceof DenseFloatArrayVector ? ((DenseFloatArrayVector) vector).getArray().clone() : ArraysEx
        .toFloatsLossy(vector.toDoubleArray()), true);
  }

  /**
   * Creates a {@link DenseFloatArrayVector} from the provided array of feature values.  The indices of these values in the vector
   * will match those in the array.
   *
   * {@link DenseFloatArrayVector} will store a <strong>copy</strong> of the passed array.  If you can tolerate linking the array
   * values to the vector values, {@link #wrap(float...)} is more efficient as it stores a reference.
   *
   * @param values the array of feature values.
   */
  public DenseFloatArrayVector(float... values) {
    this(values.clone(), true);
  }

  /**
   * Internal constructor that creates a new instance that takes ownership of the provided array.
   *
   * @param values the array wrapped by this {@link DenseFloatArrayVector}
   * @param dummyVar unused parameter to distinguish this constructor from the public constructor above
   */
  private DenseFloatArrayVector(float[] values, boolean dummyVar) {
    _values = Objects.requireNonNull(values, "values must not be null");
  }

  /**
   * Gets the underlying array of element values.  Unlike {@link #toFloatArray()}, this method does not create
   * a new array.
   *
   * <strong>Changes to the returned array will be reflected in this {@link DenseFloatArrayVector} (and vice-versa).</strong>
   */
  public float[] getArray() {
    return _values;
  }

  @Override
  public Class<? extends Number> valueType() {
    return float.class;
  }

  @Override
  public double get(long index) {
    if (index >= _values.length || index < 0) {
      return 0;
    } else {
      return _values[(int) index];
    }
  }

  @Override
  public VectorElementIterator iterator() {
    return new Iterator();
  }

  @Override
  public VectorElementIterator reverseIterator() {
    return new ReverseIterator();
  }

  @Override
  public long capacity() {
    return _values.length;
  }

  @Override
  public long maxCapacity() {
    return capacity();
  }

  @Override
  public void put(long index, double value) {
    _values[Math.toIntExact(index)] = (float) value;
  }

  @Override
  public void copyTo(float[] dest, int start, int length) {
    System.arraycopy(_values, 0, dest, start, Math.min(_values.length, length));
    if (length > _values.length) {
      // zero out remaining array elements
      Arrays.fill(dest, start + _values.length, start + length, 0);
    }
  }

  @Override
  public void transformInPlace(VectorElementTransformer transformer) {
    for (int i = 0; i < _values.length; i++) {
      if (_values[i] != 0) {
        _values[i] = (float) transformer.transform(i, _values[i]);
      }
    }
  }

  @Override
  public void addInPlace(Vector other) {
    if (other instanceof DenseFloatArrayVector) { // can be optimized
      addInPlace((DenseFloatArrayVector) other);
    } else { // do the standard thing
      MutableDenseVector.super.addInPlace(other);
    }
  }

  public void addInPlace(DenseFloatArrayVector dense) {
    for (int i = 0; i < _values.length; i++) {
      _values[i] += dense._values[i];
    }
    for (int i = _values.length; i < dense._values.length; i++) {
      if (dense._values[i] != 0) {
        throw new IndexOutOfBoundsException("Attempted to modify dense vector element outside of its internal array");
      }
    }
  }

  @Override
  public double dotProduct(Vector other) {
    if (other instanceof DenseFloatArrayVector) {
      return dotProduct((DenseFloatArrayVector) other);
    }

    return super.dotProduct(other);
  }

  /**
   * Type-specific, more efficient overload for computing the dot product.
   *
   * @param other the other vector
   * @return the dot product
   */
  public double dotProduct(DenseFloatArrayVector other) {
    int limit = Math.min(this._values.length, other._values.length);
    double result = 0;

    for (int i = 0; i < limit; i++) {
      result += ((float) this._values[i]) * ((float) other._values[i]);
    }

    return result;
  }

  private class Iterator implements VectorElementIterator {
    private int _index = 0;

    @Override
    public void forEachRemaining(VectorElementConsumer consumer) {
      for (; _index < _values.length; _index++) {
        if (_values[_index] != 0) {
          consumer.consume(_index, _values[_index]);
        }
      }
    }

    @Override
    public <T> T mapNext(VectorElementFunction<T> mapper) {
      while (_values[_index] == 0) {
        _index++;
      }
      return mapper.apply(_index, _values[_index++]);
    }

    @Override
    public void next(VectorElementConsumer consumer) {
      while (_values[_index] == 0) {
        _index++;
      }
      consumer.consume(_index, _values[_index++]);
    }

    @Override
    public boolean hasNext() {
      for (; _index < _values.length; _index++) {
        if (_values[_index] != 0) {
          return true;
        }
      }

      return false;
    }
  }

  private class ReverseIterator implements VectorElementIterator {
    private int _index = _values.length - 1;

    @Override
    public void forEachRemaining(VectorElementConsumer consumer) {
      for (; _index >= 0; _index--) {
        if (_values[_index] != 0) {
          consumer.consume(_index, _values[_index]);
        }
      }
    }

    @Override
    public <T> T mapNext(VectorElementFunction<T> mapper) {
      while (_values[_index] == 0) {
        _index--;
      }
      return mapper.apply(_index, _values[_index--]);
    }

    @Override
    public void next(VectorElementConsumer consumer) {
      while (_values[_index] == 0) {
        _index--;
      }
      consumer.consume(_index, _values[_index--]);
    }

    @Override
    public boolean hasNext() {
      for (; _index >= 0; _index--) {
        if (_values[_index] != 0) {
          return true;
        }
      }

      return false;
    }
  }
}
