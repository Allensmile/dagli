<#import "../common.ftl" as c />
<@c.AutoGeneratedWarning />
package com.linkedin.dagli.transformer;

import com.linkedin.dagli.producer.Producer;
import com.linkedin.dagli.transformer.internal.PreparedTransformer${arity}InternalAPI;
import com.linkedin.dagli.util.collection.FixedCapacityArrayList;
import com.linkedin.dagli.util.collection.UnmodifiableArrayList;
import com.linkedin.dagli.util.collection.Lists;
import java.util.List;
import java.util.ArrayList;

<#assign subclass>S extends <@c.AbstractPreparedTransformer arity "S" /></#assign>
<#assign interface><@c.PreparedTransformerInternalAPI arity "S" /></#assign>
/**
 * Base class for prepared transformers of arity ${arity} that extends {@link AbstractPreparedTransformer${arity}} to
 * allow for limited "state" across examples for greater computational efficiency.  Note that, as with all
 * {@link Producer}s, transformers deriving from this class are themselves immutable.
 *
 * Such "state" takes two forms, of which either or both may be used: the use of {@link #createExecutionCache(long)} to
 * create an "execution cache" object that will then be passed to the {@code apply(...)} method, and the
 * {@code applyAll(...)} method that transforms a number of "minibatched" examples together.
 *
<@c.TransformerParameterJavadoc arity />
 * @param <Q> the type of the execution cache object used by this instance; if an execution cache object is not used
 *            (i.e. {@link #createExecutionCache(long)} always returns null) the {@link Void} type should be specified
 * @param <S> the ultimate derived class
 */
public abstract class <@c.AbstractPreparedStatefulTransformer arity subclass />
    extends <@c.AbstractPreparedTransformer arity "S" /> {

  private static final long serialVersionUID = 1;

  public AbstractPreparedStatefulTransformer${arity}() {
    super();
  }

  public AbstractPreparedStatefulTransformer${arity}(<@c.InputProducerList arity />) {
    super(<@c.InputSuffixedList "input" arity />);
  }

  @Override
  public R apply(<@c.InputSuffixedParameters "value" arity />) {
    return apply(createExecutionCache(1), <@c.InputSuffixedList "value" arity />);
  }

  /**
   * Applies the transformer to the given input values.
   *
   * This method must be thread-safe, able to be invoked concurrently on the same instance.
   *
   * @param executionCache a transitory object used for sharing temporary data across invocations of this transformer,
   *                       as returned by {@link #createExecutionCache(long)}; may be null
<#list 1..arity as index>   * @param value${c.InputSuffix(index)} the ${index}'th input value
</#list>
   * @return the result of applying this transformer to the given example
   */
  protected abstract R apply(Q executionCache, <@c.InputSuffixedParameters "value" arity />);

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
<#list 1..arity as index>   * @param values${index} a parallel list of inputs corresponding to the examples being transformed; must not be modified
   *                   or stored past the end of the method call
</#list>
   * @param results a list to which should be added the results of applying this transformer to the given examples, in
   *                the same order the examples were provided; must not be stored past the end of the method call
   */
  protected void applyAll(Q executionCache, <@c.ValuesArguments "List" arity "values" />, List<? super R> results) {
    ArrayList<R> result = new ArrayList<>(values1.size());
    for (int i = 0; i < values1.size(); i++) {
      results.add(apply(executionCache, <@c.InputSuffixedList "values" arity ".get(i)" />));
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
  protected <@c.PreparedTransformerInternalAPI arity, "S" /> createInternalAPI() {
    return new InternalAPI();
  }

  protected class InternalAPI extends <@c.AbstractPreparedTransformer arity "S" />.InternalAPI {
    @Override
    public R apply(Object executionCache, <@c.InputSuffixedParameters "value" arity />) {
      return AbstractPreparedStatefulTransformer${arity}.this.apply((Q) executionCache, <@c.InputSuffixedList "value" arity />);
    }

    @Override
    public Object createExecutionCache(long exampleCountGuess) {
      return AbstractPreparedStatefulTransformer${arity}.this.createExecutionCache(exampleCountGuess);
    }

    @Override
    @SuppressWarnings("unchecked") // correct typing asserted by the semantics of the DAG
    public void applyAllUnsafe(Object executionCache, int count, Object[][] values, Object[] results) {
      AbstractPreparedStatefulTransformer${arity}.this.applyAll((Q) executionCache,
        <#list 1..arity as index>new UnmodifiableArrayList<>((${c.InputGenericArgument(index)}[]) values[${index-1}], count)<#sep>, </#list>,
        new FixedCapacityArrayList<>(results));
    }

    @Override
    @SuppressWarnings("unchecked") // correct typing asserted by the semantics of the DAG
    public void applyAllUnsafe(Object executionCache, int count, List<? extends List<?>> values,
      List<? super R> results) {
      AbstractPreparedStatefulTransformer${arity}.this.applyAll((Q) executionCache,
        <#list 1..arity as index>(List<${c.InputGenericArgument(index)}>) Lists.limit(values.get(${index-1}), count)<#sep>, </#list>,
        results);
    }

    @Override
    public int getPreferredMinibatchSize() {
      return AbstractPreparedStatefulTransformer${arity}.this.getPreferredMinibatchSize();
    }
  }
}
