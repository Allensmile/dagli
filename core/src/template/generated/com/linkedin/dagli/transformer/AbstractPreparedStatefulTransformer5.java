// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the transformer/AbstractPreparedStatefulTransformerX.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.transformer;

import com.linkedin.dagli.producer.Producer;
import com.linkedin.dagli.transformer.internal.PreparedTransformer5InternalAPI;
import com.linkedin.dagli.util.collection.FixedCapacityArrayList;
import com.linkedin.dagli.util.collection.UnmodifiableArrayList;
import com.linkedin.dagli.util.collection.Lists;
import java.util.List;
import java.util.ArrayList;


/**
 * Base class for prepared transformers of arity 5 that extends {@link AbstractPreparedTransformer5} to
 * allow for limited "state" across examples for greater computational efficiency.  Note that, as with all
 * {@link Producer}s, transformers deriving from this class are themselves immutable.
 *
 * Such "state" takes two forms, of which either or both may be used: the use of {@link #createExecutionCache(long)} to
 * create an "execution cache" object that will then be passed to the {@code apply(...)} method, and the
 * {@code applyAll(...)} method that transforms a number of "minibatched" examples together.
 *
 * @param <A> the type of the first input to the transformer
 * @param <B> the type of the second input to the transformer
 * @param <C> the type of the third input to the transformer
 * @param <D> the type of the fourth input to the transformer
 * @param <E> the type of the fifth input to the transformer
 * @param <R> the type of result produced by the transformer
 * @param <Q> the type of the execution cache object used by this instance; if an execution cache object is not used
 *            (i.e. {@link #createExecutionCache(long)} always returns null) the {@link Void} type should be specified
 * @param <S> the ultimate derived class
 */
public abstract class AbstractPreparedStatefulTransformer5<A, B, C, D, E, R, Q, S extends AbstractPreparedTransformer5<A, B, C, D, E, R, S>>
    extends AbstractPreparedTransformer5<A, B, C, D, E, R, S> {

  private static final long serialVersionUID = 1;

  public AbstractPreparedStatefulTransformer5() {
    super();
  }

  public AbstractPreparedStatefulTransformer5(Producer<? extends A> input1, Producer<? extends B> input2,
      Producer<? extends C> input3, Producer<? extends D> input4, Producer<? extends E> input5) {
    super(input1, input2, input3, input4, input5);
  }

  @Override
  public R apply(A value1, B value2, C value3, D value4, E value5) {
    return apply(createExecutionCache(1), value1, value2, value3, value4, value5);
  }

  /**
   * Applies the transformer to the given input values.
   *
   * This method must be thread-safe, able to be invoked concurrently on the same instance.
   *
   * @param executionCache a transitory object used for sharing temporary data across invocations of this transformer,
   *                       as returned by {@link #createExecutionCache(long)}; may be null
   * @param value1 the 1'th input value
   * @param value2 the 2'th input value
   * @param value3 the 3'th input value
   * @param value4 the 4'th input value
   * @param value5 the 5'th input value
   * @return the result of applying this transformer to the given example
   */
  protected abstract R apply(Q executionCache, A value1, B value2, C value3, D value4, E value5);

  /**
   * Applies the transformer to all provided examples, whose input values are provided as parallel {@code values} lists
   * (the inputs for, e.g. the fifth example are given by {@code values1.get(4), values2.get(4)...})
   *
   * The number of examples passed can be any amount greater than 0; {@link #getPreferredMinibatchSize()} acts as a
   * "hint" to the DAG executor but will not necessarily be honored.
   *
   * This method must be thread-safe, able to be invoked concurrently on the same instance.
   *
   * Additionally, the provided {@code values} lists must not be modified, and neither they nor the {@code results}
   * list should they be stored past the end of the method call since their underlying data structures may be reused
   * and modified by Dagli after this call is finished.
   *
   * Finally, the ordering of the examples passed to this method may differ from their original ordering (as supplied
   * to the encapsulating DAG).
   *
   * By default, this implementation transforms the provided examples via repeated calls to the {@code apply(...)}
   * method.  Transformers that benefit from minibatching of their examples should override this method.
   *
   * @param executionCache a transitory object used for sharing temporary data across invocations of this transformer,
   *                       as returned by {@link #createExecutionCache(long)}; may be null
   * @param values1 a parallel list of inputs corresponding to the examples being transformed; must not be modified
   *                   or stored past the end of the method call
   * @param values2 a parallel list of inputs corresponding to the examples being transformed; must not be modified
   *                   or stored past the end of the method call
   * @param values3 a parallel list of inputs corresponding to the examples being transformed; must not be modified
   *                   or stored past the end of the method call
   * @param values4 a parallel list of inputs corresponding to the examples being transformed; must not be modified
   *                   or stored past the end of the method call
   * @param values5 a parallel list of inputs corresponding to the examples being transformed; must not be modified
   *                   or stored past the end of the method call
   * @param results a list to which should be added the results of applying this transformer to the given examples, in
   *                the same order the examples were provided; must not be stored past the end of the method call
   */
  protected void applyAll(Q executionCache, List<? extends A> values1, List<? extends B> values2,
      List<? extends C> values3, List<? extends D> values4, List<? extends E> values5, List<? super R> results) {
    ArrayList<R> result = new ArrayList<>(values1.size());
    for (int i = 0; i < values1.size(); i++) {
      results
          .add(apply(executionCache, values1.get(i), values2.get(i), values3.get(i), values4.get(i), values5.get(i)));
    }
  }

  /**
   * Creates an execution cache object for this transformer (or returns null).
   *
   * The returned instance will be passed to subsequent {@code apply(...)} and {@code applyAll(...)} calls.  A guess of
   * the number of examples that will be encountered using this execution ache object is provided; transformers that do
   * not benefit from caching for this number of examples should return null.
   *
   * Execution cache objects may be concurrently used by multiple threads.  Aside from this, they do not need to meet
   * any other criteria (such as implementing {@link java.io.Serializable}.)  If the object implements
   * {@link AutoCloseable} it <strong>may</strong> be closed when it is no longer required by the DAG executor; however,
   * the correctness of an implementation should not depend on {@link AutoCloseable#close()} being called at a
   * particular time, or ever.
   *
   * Multiple execution cache objects may be created and used during a single DAG execution; correctness must therefore
   * not depend on, e.g. accruing state within the execution cache object.  Additionally, execution caches for the same
   * (reference-equals) transformer within the same DAG may potentially be shared across multiple DAG executions.
   *
   * The default implementation of this method simply returns null.
   *
   * @param exampleCountGuess the best available guess of how many examples will be processed using the returned
   *                          execution cache, or {@link Long#MAX_VALUE} if the potential number is unbounded
   * @return an execution cache instance to be passed to subsequent calls to the "apply" methods, or null
   */
  protected Q createExecutionCache(long exampleCountGuess) {
    return null;
  }

  /**
   * Gets the preferred minibatch size when using {@code applyAll(...)}.  The DAG executor is free to ignore this
   * preference and may provide much larger or smaller minibatches.
   *
   * The default implementation returns a value of 1, indicating that this transformer is indifferent to minibatching;
   * derived classes that benefit from minibatching should override this method to return a more appropriate value.
   *
   * @return the minibatch size preferred by the transformer
   */
  protected int getPreferredMinibatchSize() {
    return 1;
  }

  @Override
  protected PreparedTransformer5InternalAPI<A, B, C, D, E, R, S> createInternalAPI() {
    return new InternalAPI();
  }

  protected class InternalAPI extends AbstractPreparedTransformer5<A, B, C, D, E, R, S>.InternalAPI {
    @Override
    public R apply(Object executionCache, A value1, B value2, C value3, D value4, E value5) {
      return AbstractPreparedStatefulTransformer5.this
          .apply((Q) executionCache, value1, value2, value3, value4, value5);
    }

    @Override
    public Object createExecutionCache(long exampleCountGuess) {
      return AbstractPreparedStatefulTransformer5.this.createExecutionCache(exampleCountGuess);
    }

    @Override
    @SuppressWarnings("unchecked")
    // correct typing asserted by the semantics of the DAG
    public void applyAllUnsafe(Object executionCache, int count, Object[][] values, Object[] results) {
      AbstractPreparedStatefulTransformer5.this.applyAll((Q) executionCache, new UnmodifiableArrayList<>(
          (A[]) values[0], count), new UnmodifiableArrayList<>((B[]) values[1], count), new UnmodifiableArrayList<>(
          (C[]) values[2], count), new UnmodifiableArrayList<>((D[]) values[3], count), new UnmodifiableArrayList<>(
          (E[]) values[4], count), new FixedCapacityArrayList<>(results));
    }

    @Override
    @SuppressWarnings("unchecked")
    // correct typing asserted by the semantics of the DAG
    public void applyAllUnsafe(Object executionCache, int count, List<? extends List<?>> values, List<? super R> results) {
      AbstractPreparedStatefulTransformer5.this.applyAll((Q) executionCache,
          (List<A>) Lists.limit(values.get(0), count), (List<B>) Lists.limit(values.get(1), count),
          (List<C>) Lists.limit(values.get(2), count), (List<D>) Lists.limit(values.get(3), count),
          (List<E>) Lists.limit(values.get(4), count), results);
    }

    @Override
    public int getPreferredMinibatchSize() {
      return AbstractPreparedStatefulTransformer5.this.getPreferredMinibatchSize();
    }
  }
}
