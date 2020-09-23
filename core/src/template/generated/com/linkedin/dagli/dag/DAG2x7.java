// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the dag/DAGXxY.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.dag;

import java.util.Collection;
import java.util.Collections;

import com.linkedin.dagli.annotation.equality.IgnoredByValueEquality;
import com.linkedin.dagli.annotation.equality.ValueEquality;
import com.linkedin.dagli.objectio.ObjectIterator;
import com.linkedin.dagli.objectio.ObjectReader;
import com.linkedin.dagli.preparer.Preparer2;
import com.linkedin.dagli.preparer.AbstractBatchPreparer2;
import com.linkedin.dagli.preparer.PreparerContext;
import com.linkedin.dagli.preparer.PreparerResultMixed;
import com.linkedin.dagli.producer.Producer;
import com.linkedin.dagli.reducer.Reducer;
import com.linkedin.dagli.transformer.AbstractPreparableTransformer2;
import com.linkedin.dagli.transformer.AbstractPreparedTransformer2;
import com.linkedin.dagli.transformer.PreparedTransformer2;
import com.linkedin.dagli.util.array.AutoCloseableArray;

import com.linkedin.dagli.generator.Generator;
import com.linkedin.dagli.placeholder.Placeholder;

import com.linkedin.dagli.tuple.Tuple7;
import com.linkedin.dagli.tuple.Tuple2;

import com.linkedin.dagli.transformer.Value0FromTuple;
import com.linkedin.dagli.transformer.Value1FromTuple;
import com.linkedin.dagli.transformer.Value2FromTuple;
import com.linkedin.dagli.transformer.Value3FromTuple;
import com.linkedin.dagli.transformer.Value4FromTuple;
import com.linkedin.dagli.transformer.Value5FromTuple;
import com.linkedin.dagli.transformer.Value6FromTuple;


/**
 * DAGs, directed acyclic graphs, contain root nodes ({@link com.linkedin.dagli.placeholder.Placeholder}s and {@link com.linkedin.dagli.generator.Generator}s) and child nodes
 * ({@link com.linkedin.dagli.transformer.PreparedTransformer}s,
 * {@link com.linkedin.dagli.transformer.PreparableTransformer}s, and {@link com.linkedin.dagli.view.TransformerView}s).
 *
 * DAG2x7 is a <i>preparable</i> DAG.
 *
 * Before it can be used to transform new examples, it must be prepared with training data (e.g. by calling the
 * prepare() method).  The prepareAndApply() method both trains the DAG and applies it to the training data, yielding
 * both the prepared DAG and the results of applying the DAG to the training data (this can be useful in autoevaluation,
 * e.g. how well a classification model performs when predicting labels for its own training data).
 *
 * DAG2x7 is also a {@link com.linkedin.dagli.transformer.PreparableTransformer} and can be used as
 * a transformer within other DAGs.
 *
 * @param <A> the type of the first input
 * @param <B> the type of the second input
 * @param <RA> the type of the first result
 * @param <RB> the type of the second result
 * @param <RC> the type of the third result
 * @param <RD> the type of the fourth result
 * @param <RE> the type of the fifth result
 * @param <RF> the type of the sixth result
 * @param <RG> the type of the seventh result
 */
@ValueEquality
public class DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG>
    extends
    AbstractPreparableTransformer2<A, B, Tuple7<RA, RB, RC, RD, RE, RF, RG>, DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>, DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG>>
    implements
    PreparableDAGTransformer<Tuple7<RA, RB, RC, RD, RE, RF, RG>, DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>, DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG>> {

  private static final long serialVersionUID = 1;

  // the DAGStructure stores the actual graph
  private DAGStructure<Tuple7<RA, RB, RC, RD, RE, RF, RG>> _dag;

  // keep track of what level of reduction we used to construct this DAG; this will be carried over to the prepared DAG
  @IgnoredByValueEquality
  // irrelevant given _dag and not (directly) externally observable
  private Reducer.Level _reductionLevel = null; // null -> no reductions applied

  // remember what executor should be used to prepare the DAG; this will also be "inherited" by the prepared DAG and
  // used for inference
  @IgnoredByValueEquality
  // not a factor in the semantics of the DAG and not (directly) externally observable
  private DAGExecutor _executor;

  /**
   * Creates a new DAG instance from the specified {@link DAGStructure} graph with the specified inputs.  DAG objects
   * have inputs because they can also serve as transformers within other DAGs.  In the common case where the DAG is
   * used directly and not embedded within another DAG, these inputs don't matter (and can simply be left as
   * {@link com.linkedin.dagli.producer.MissingInput}s).
   *
   * By default, this DAG will use a {@link LocalDAGExecutor}; to use a different executor, use the
   * {@link #withExecutor(DAGExecutor)} method.  When this DAG is prepared, the DAG executor will be "inherited" by the
   * resultant prepared DAG.
   *
   * @param dag the {@link DAGStructure} representing the actual graph of nodes and directed (parent/child) edges
   * @param input1 the {@link Producer} providing the first input to the transformer
   * @param input2 the {@link Producer} providing the second input to the transformer
   */
  DAG2x7(DAGStructure<Tuple7<RA, RB, RC, RD, RE, RF, RG>> dag, Producer<? extends A> input1,
      Producer<? extends B> input2) {
    super(input1, input2);
    _dag = dag;
    _executor = new LocalDAGExecutor();
  }

  /**
   * Returns a DAG derived from this one that has been reduced to at least the specified level.
   *
   * If level is less than this DAG's current reduction level, {@code this} DAG is returned.
   *
   * Otherwise, this DAG is reduced, and the new, reduced DAG is returned.
   *
   * @param level the level of reduction desired; all reducers at or above this level will be applied
   * @return a DAG that has been reduced to at least the specified level (possibly this same DAG)
   */
  @Override
  public DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG> withReduction(Reducer.Level level) {
    if (Reducer.Level.compare(level, _reductionLevel) >= 0) {
      return this;
    }

    DeduplicatedDAG reduced = DAGReducer.reduce(new DeduplicatedDAG(_dag), level);
    return clone(c -> {
      c._dag = new DAGStructure<>(reduced);
      c._reductionLevel = level;
    });
  }

  @Override
  public InternalAPI internalAPI() {
    return new InternalAPI();
  }

  public class InternalAPI
      extends
      AbstractPreparableTransformer2<A, B, Tuple7<RA, RB, RC, RD, RE, RF, RG>, DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>, DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG>>.InternalAPI
      implements
      PreparableDAGTransformer.InternalAPI<Tuple7<RA, RB, RC, RD, RE, RF, RG>, DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>, DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG>> {
    @Override
    public DAGStructure<Tuple7<RA, RB, RC, RD, RE, RF, RG>> getDAGStructure() {
      return _dag;
    }

    @Override
    public Reducer.Level getReductionLevel() {
      return _reductionLevel;
    }

    @Override
    public DAGExecutor getDAGExecutor() {
      return _executor;
    }

    @Override
    public DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG> getInstance() {
      return DAG2x7.this;
    }
  }

  @Override
  protected Collection<? extends Reducer<? super DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG>>> getGraphReducers() {
    return Collections.singletonList(DAGTransformerReducer.INSTANCE);
  }

  @Override
  protected boolean hasAlwaysConstantResult() {
    return _dag._isAlwaysConstant;
  }

  /**
   * Returns a copy of this DAG that will use the given {@link DAGExecutor} that will be used to execute this DAG.
   *
   * This executor will also be "inherited" by the resultant prepared DAG.
   *
   * @param executor the {@link DAGExecutor} to use
   * @return a copy of this instance that will use the provided executor
   */
  public DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG> withExecutor(DAGExecutor executor) {
    return clone(r -> r._executor = executor);
  }

  @Override
  protected boolean hasIdempotentPreparer() {
    return _dag._hasIdempotentPreparer;
  }

  /**
   * Sets the parents of this transformer.  The results of the parent nodes will be the inputs to the transformer.
   *
   * @param input1 the {@link Producer} providing the first input to the transformer
   * @param input2 the {@link Producer} providing the second input to the transformer
   * @return a copy of this instance that will have the specified parents
   */
  public DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG> withInputs(Producer<? extends A> input1, Producer<? extends B> input2) {
    return super.withAllInputs(input1, input2);
  }

  /**
   * Creates a new DAG that invokes this transformer, except that the first input is replaced by the specified {@link Generator}.
   * The resultant DAG will thus have an arity of 1 (one less than this transformer), and values that were formerly
   * provided as the first input to the transformer will now instead be generated by the {@link Generator}.
   *
   * This method is useful for eliding inputs to a transformer.  For example, a prepared classifier might have a
   * "vestigial" label input (inherited from a corresponding preparable classifier from which it was obtained) that can
   * be replaced by <code>Constant.nullValue()</code>.  Since the label input isn't used by the
   * prepared classifier (instead, it predicts a label from its other inputs and produces this as its result) replacing
   * it with a null has no effect and eliminates an unnecessary input.
   *
   * @param generator the generator that will provide a value for the first input
   * @return a new DAG that invokes this transformer with the same inputs, except the first, whose value is replaced by
   *         the produced by the provided generator
   */
  public DAG1x7<B, RA, RB, RC, RD, RE, RF, RG> withGeneratorAsInput1(Generator<A> generator) {
    Placeholder<B> nestedPlaceholder2 = new Placeholder<>("Original Input 2");
    DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG> dag = this.withInputs(generator, nestedPlaceholder2);

    return DAG
        .withPlaceholder(nestedPlaceholder2)
        .withOutputs(new Value0FromTuple<>(dag), new Value1FromTuple<>(dag), new Value2FromTuple<>(dag),
            new Value3FromTuple<>(dag), new Value4FromTuple<>(dag), new Value5FromTuple<>(dag),
            new Value6FromTuple<>(dag)).withExecutor(_executor).withInput(getInput2());
  }

  /**
   * Creates a new DAG that invokes this transformer, except that the second input is replaced by the specified {@link Generator}.
   * The resultant DAG will thus have an arity of 1 (one less than this transformer), and values that were formerly
   * provided as the second input to the transformer will now instead be generated by the {@link Generator}.
   *
   * This method is useful for eliding inputs to a transformer.  For example, a prepared classifier might have a
   * "vestigial" label input (inherited from a corresponding preparable classifier from which it was obtained) that can
   * be replaced by <code>Constant.nullValue()</code>.  Since the label input isn't used by the
   * prepared classifier (instead, it predicts a label from its other inputs and produces this as its result) replacing
   * it with a null has no effect and eliminates an unnecessary input.
   *
   * @param generator the generator that will provide a value for the second input
   * @return a new DAG that invokes this transformer with the same inputs, except the second, whose value is replaced by
   *         the produced by the provided generator
   */
  public DAG1x7<A, RA, RB, RC, RD, RE, RF, RG> withGeneratorAsInput2(Generator<B> generator) {
    Placeholder<A> nestedPlaceholder1 = new Placeholder<>("Original Input 1");
    DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG> dag = this.withInputs(nestedPlaceholder1, generator);

    return DAG
        .withPlaceholder(nestedPlaceholder1)
        .withOutputs(new Value0FromTuple<>(dag), new Value1FromTuple<>(dag), new Value2FromTuple<>(dag),
            new Value3FromTuple<>(dag), new Value4FromTuple<>(dag), new Value5FromTuple<>(dag),
            new Value6FromTuple<>(dag)).withExecutor(_executor).withInput(getInput1());
  }

  /**
   * The {@link com.linkedin.dagli.preparer.Preparer} that prepares (trains) the DAG.
   *
   * @param <A> the type of the first input
   * @param <B> the type of the second input
   * @param <RA> the type of the first result
   * @param <RB> the type of the second result
   * @param <RC> the type of the third result
   * @param <RD> the type of the fourth result
   * @param <RE> the type of the fifth result
   * @param <RF> the type of the sixth result
   * @param <RG> the type of the seventh result
   */
  private static class Preparer<A, B, RA, RB, RC, RD, RE, RF, RG>
      extends
      AbstractBatchPreparer2<A, B, Tuple7<RA, RB, RC, RD, RE, RF, RG>, DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>> {
    // the DAG being prepared
    private final DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG> _dag;

    /**
     * Creates a new instance.
     *
     * @param dag the DAG that will be prepared
     */
    Preparer(DAG2x7<A, B, RA, RB, RC, RD, RE, RF, RG> dag) {
      _dag = dag;
    }

    @Override
    @SuppressWarnings("unchecked")
    // we know the returned type is correct by the semantics of executors
    public PreparerResultMixed<PreparedTransformer2<A, B, Tuple7<RA, RB, RC, RD, RE, RF, RG>>, DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>> finish(
        ObjectReader<Tuple2<A, B>> inputs) {
      return (PreparerResultMixed) _dag._executor.internalAPI().prepareUnsafe(_dag,
          ObjectReader.split(2, inputs.lazyMap(tuple -> tuple.toArray())));
    }

    @Override
    public void process(A value1, B value2) {
      // noop
    }
  }

  @Override
  protected Preparer2<A, B, Tuple7<RA, RB, RC, RD, RE, RF, RG>, DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>> getPreparer(
      PreparerContext context) {
    return new Preparer<>(this);
  }

  /**
   * Prepares (trains) the DAG on the provided input data (examples), and applies it to this same data.  The returned
   * {@link Result} instance contains both the prepared DAG as well as the results from applying the DAG on the
   * preparation data.
   *
   * The inputs to this method take the form of parallel sequences of values.  Each sequence will provide the values
   * for a particular {@link com.linkedin.dagli.placeholder.Placeholder} in the DAG (the ordering of the
   * {@link com.linkedin.dagli.placeholder.Placeholder}s in a DAG is determined by their
   * ordering in the call to "DAG.withPlaceholders(...)...." that created it).  They are "parallel" because, e.g. all the
   * values corresponding to the fourth example will be the fourth value in their respective input sequences (all
   * sequences must, of course, be equal in size).
   *
   * Note that the results from applying the DAG to the preparation data with this method are not necessarily the same
   * results that would be obtained from calling the apply(...) method on prepared DAG with the same data.  This is
   * because certain transformers (e.g. KFoldCrossTrained and PreparedByGroup) intentionally transform training data in
   * a different way than inference data.  Please see the documentation of these transformers for details.
   *
   * @param values1 the sequence of values for the first
   * {@link com.linkedin.dagli.placeholder.Placeholder} in the DAG.
   * @param values2 the sequence of values for the second
   * {@link com.linkedin.dagli.placeholder.Placeholder} in the DAG.
   * @return a {@link Result} instance containing both the prepared DAG and the result of applying the DAG on the
   *         preparation data
   */
  public Result<A, B, RA, RB, RC, RD, RE, RF, RG> prepareAndApply(Iterable<? extends A> values1,
      Iterable<? extends B> values2) {
    DAGExecutionResult<Tuple7<RA, RB, RC, RD, RE, RF, RG>, DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>> res =
        _executor.internalAPI().prepareAndApplyUnsafe(this,
            new ObjectReader[] { ObjectReader.wrap(values1), ObjectReader.wrap(values2) });
    return new Result<>((DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>) res.getPreparerResult()
        .getPreparedTransformerForNewData(), res.getOutputs());
  }

  /**
   * Prepares (trains) the DAG on the provided input data (examples).  The resultant prepared (trained) DAG can then be
   * applied to new examples (inference).
   *
   * The inputs to this method take the form of parallel sequences of values.  Each sequence will provide the values
   * for a particular {@link com.linkedin.dagli.placeholder.Placeholder} in the DAG (the ordering of the
   * {@link com.linkedin.dagli.placeholder.Placeholder}s in a DAG is determined by their
   * ordering in the call to "DAG.withPlaceholders(...)...." that created it).  They are "parallel" because, e.g. all the
   * values corresponding to the fourth example will be the fourth value in their respective input sequences (all
   * sequences must, of course, be equal in size).
   *
   * @param values1 the sequence of values for the first
   * {@link com.linkedin.dagli.placeholder.Placeholder} in the DAG.
   * @param values2 the sequence of values for the second
   * {@link com.linkedin.dagli.placeholder.Placeholder} in the DAG.
   * @return a prepared (trained) DAG that can be applied to new examples (inference)
   */
  public DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG> prepare(Iterable<? extends A> values1,
      Iterable<? extends B> values2) {
    return (DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>) _executor.internalAPI()
        .prepareUnsafe(this, new ObjectReader[] { ObjectReader.wrap(values1), ObjectReader.wrap(values2) })
        .getPreparedTransformerForNewData();
  }

  /**
   * {@link Result}s are returned by the DAG's prepareAndApply(...) methods and contain both the prepared (trained) DAG
   * and the results of applying that DAG to the preparation (training) data.
   *
  * @param <A> the type of the first input
  * @param <B> the type of the second input
  * @param <RA> the type of the first result
  * @param <RB> the type of the second result
  * @param <RC> the type of the third result
  * @param <RD> the type of the fourth result
  * @param <RE> the type of the fifth result
  * @param <RF> the type of the sixth result
  * @param <RG> the type of the seventh result
   */
  public static final class Result<A, B, RA, RB, RC, RD, RE, RF, RG> extends
      AbstractDAGResult7<RA, RB, RC, RD, RE, RF, RG> {
    // the prepared DAG
    private final DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG> _preparedDAG;

    /**
     * @return the {@link Prepared} DAG that was prepared (trained) on the provided preparation (training) data.
     */
    public DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG> getPreparedDAG() {
      return _preparedDAG;
    }

    /**
     * Creates a new instance.
     *
     * @param preparedDAG the prepared DAG to be stored in this instance
     * @param results an array of {@link ObjectReader}s that contain each resultant output value of applying the DAG to
     *                the preparation (training) data
     */
    Result(DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG> preparedDAG, ObjectReader<?>[] results) {
      super(results);
      _preparedDAG = preparedDAG;
    }
  }

  /**
   * Prepared DAGs (directed acyclic graphs) contain root nodes ({@link com.linkedin.dagli.placeholder.Placeholder}s and
   * {@link com.linkedin.dagli.generator.Generator}s) and child nodes
   * ({@link com.linkedin.dagli.transformer.PreparedTransformer}s)  Unlike preparable DAGs, prepared DAGs do not contain
   * {@link com.linkedin.dagli.transformer.PreparableTransformer}s nor {@link com.linkedin.dagli.view.TransformerView}s
   * and can be applied to examples to transform them to some desired result (often an inference, such as a
   * classification or regression) using the apply(...) (for single examples) or applyAll(...) (for multiple examples)
   * methods.
   *
   * Prepared DAGs are also themselves {@link com.linkedin.dagli.transformer.PreparedTransformer}s, and can be used as
   * such within another DAG.
   *
  * @param <A> the type of the first input
  * @param <B> the type of the second input
  * @param <RA> the type of the first result
  * @param <RB> the type of the second result
  * @param <RC> the type of the third result
  * @param <RD> the type of the fourth result
  * @param <RE> the type of the fifth result
  * @param <RF> the type of the sixth result
  * @param <RG> the type of the seventh result
   */
  @ValueEquality
  public static class Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>
      extends
      AbstractPreparedTransformer2<A, B, Tuple7<RA, RB, RC, RD, RE, RF, RG>, DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>>
      implements
      PreparedDAGTransformer<Tuple7<RA, RB, RC, RD, RE, RF, RG>, DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>> {

    private static final long serialVersionUID = 1;

    // the DAGStructure stores the actual graph
    private DAGStructure<Tuple7<RA, RB, RC, RD, RE, RF, RG>> _dag;

    // keep track of what level of reduction we used to construct this DAG
    @IgnoredByValueEquality
    // irrelevant given _dag and not (directly) externally observable
    private Reducer.Level _reductionLevel = null; // null -> no reductions applied

    // the executor that will be used to apply the DAG to new examples
    @IgnoredByValueEquality
    // not a factor in the semantics of the DAG and not (directly) externally observable
    private PreparedDAGExecutor _executor;

    /**
     * Creates a new DAG instance from the specified {@link DAGStructure} graph with the specified inputs.  DAG objects
     * have inputs because they can also serve as transformers within other DAGs.  In the common case where the DAG is
     * used directly and not embedded within another DAG, these inputs don't matter (and can simply be left as
     * {@link com.linkedin.dagli.producer.MissingInput}s).
     *
     * By default, this DAG will use a {@link LocalDAGExecutor}; to use a different executor, use the
     * {@link #withExecutor(DAGExecutor)} method.
     *
     * @param dag the {@link DAGStructure} representing the actual graph of nodes and directed (parent/child) edges
    * @param input1 the {@link Producer} providing the first input to the transformer
    * @param input2 the {@link Producer} providing the second input to the transformer
     */
    Prepared(DAGStructure<Tuple7<RA, RB, RC, RD, RE, RF, RG>> dag, Producer<? extends A> input1,
        Producer<? extends B> input2) {
      super(input1, input2);
      _dag = dag;
      _executor = new LocalDAGExecutor();
    }

    /**
     * Returns a DAG derived from this one that has been reduced to at least the specified level.
     *
     * If level is less than this DAG's current reduction level, {@code this} DAG is returned.
     *
     * Otherwise, this DAG is reduced, and the new, reduced DAG is returned.
     *
     * @param level the level of reduction desired; all reducers at or above this level will be applied
     * @return a DAG that has been reduced to at least the specified level (possibly this same DAG)
     */
    @Override
    public DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG> withReduction(Reducer.Level level) {
      if (Reducer.Level.compare(level, _reductionLevel) >= 0) {
        return this;
      }

      DeduplicatedDAG reduced = DAGReducer.reduce(new DeduplicatedDAG(_dag), level);
      return clone(c -> {
        c._dag = new DAGStructure<>(reduced);
        c._reductionLevel = level;
      });
    }

    @Override
    public InternalAPI internalAPI() {
      return new InternalAPI();
    }

    public class InternalAPI
        extends
        AbstractPreparedTransformer2<A, B, Tuple7<RA, RB, RC, RD, RE, RF, RG>, DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>>.InternalAPI
        implements
        PreparedDAGTransformer.InternalAPI<Tuple7<RA, RB, RC, RD, RE, RF, RG>, DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>> {
      @Override
      public DAGStructure<Tuple7<RA, RB, RC, RD, RE, RF, RG>> getDAGStructure() {
        return _dag;
      }

      @Override
      public Reducer.Level getReductionLevel() {
        return _reductionLevel;
      }

      @Override
      public PreparedDAGExecutor getDAGExecutor() {
        return _executor;
      }

      @Override
      public DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG> getInstance() {
        return DAG2x7.Prepared.this;
      }
    }

    @Override
    protected Collection<? extends Reducer<? super DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG>>> getGraphReducers() {
      return Collections.singletonList(DAGTransformerReducer.INSTANCE);
    }

    @Override
    protected boolean hasAlwaysConstantResult() {
      return _dag._isAlwaysConstant;
    }

    /**
     * Returns a copy of this DAG that will use the given {@link PreparedDAGExecutor} that will be used to execute this DAG.

     *
     * @param executor the {@link DAGExecutor} to use
     * @return a copy of this instance that will use the provided executor
     */
    public DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG> withExecutor(PreparedDAGExecutor executor) {
      return clone(r -> r._executor = executor);
    }

    /**
     * Sets the parents of this transformer.  The results of the parent nodes will be the inputs to the transformer.
     *
     * @param input1 the {@link Producer} providing the first input to the transformer
     * @param input2 the {@link Producer} providing the second input to the transformer
     * @return a copy of this instance that will have the specified parents
     */
    public DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG> withInputs(Producer<? extends A> input1,
        Producer<? extends B> input2) {
      return super.withAllInputs(input1, input2);
    }

    /**
     * Creates a new DAG that invokes this transformer, except that the first input is replaced by the specified {@link Generator}.
     * The resultant DAG will thus have an arity of 1 (one less than this transformer), and values that were formerly
     * provided as the first input to the transformer will now instead be generated by the {@link Generator}.
     *
     * This method is useful for eliding inputs to a transformer.  For example, a prepared classifier might have a
     * "vestigial" label input (inherited from a corresponding preparable classifier from which it was obtained) that can
     * be replaced by <code>Constant.nullValue()</code>.  Since the label input isn't used by the
     * prepared classifier (instead, it predicts a label from its other inputs and produces this as its result) replacing
     * it with a null has no effect and eliminates an unnecessary input.
     *
     * @param generator the generator that will provide a value for the first input
     * @return a new DAG that invokes this transformer with the same inputs, except the first, whose value is replaced by
     *         the produced by the provided generator
     */
    public DAG1x7.Prepared<B, RA, RB, RC, RD, RE, RF, RG> withGeneratorAsInput1(Generator<A> generator) {
      Placeholder<B> nestedPlaceholder2 = new Placeholder<>("Original Input 2");
      DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG> dag = this.withInputs(generator, nestedPlaceholder2);

      return DAG.Prepared
          .withPlaceholder(nestedPlaceholder2)
          .withOutputs(new Value0FromTuple<>(dag), new Value1FromTuple<>(dag), new Value2FromTuple<>(dag),
              new Value3FromTuple<>(dag), new Value4FromTuple<>(dag), new Value5FromTuple<>(dag),
              new Value6FromTuple<>(dag)).withExecutor(_executor).withInput(getInput2());
    }

    /**
     * Creates a new DAG that invokes this transformer, except that the second input is replaced by the specified {@link Generator}.
     * The resultant DAG will thus have an arity of 1 (one less than this transformer), and values that were formerly
     * provided as the second input to the transformer will now instead be generated by the {@link Generator}.
     *
     * This method is useful for eliding inputs to a transformer.  For example, a prepared classifier might have a
     * "vestigial" label input (inherited from a corresponding preparable classifier from which it was obtained) that can
     * be replaced by <code>Constant.nullValue()</code>.  Since the label input isn't used by the
     * prepared classifier (instead, it predicts a label from its other inputs and produces this as its result) replacing
     * it with a null has no effect and eliminates an unnecessary input.
     *
     * @param generator the generator that will provide a value for the second input
     * @return a new DAG that invokes this transformer with the same inputs, except the second, whose value is replaced by
     *         the produced by the provided generator
     */
    public DAG1x7.Prepared<A, RA, RB, RC, RD, RE, RF, RG> withGeneratorAsInput2(Generator<B> generator) {
      Placeholder<A> nestedPlaceholder1 = new Placeholder<>("Original Input 1");
      DAG2x7.Prepared<A, B, RA, RB, RC, RD, RE, RF, RG> dag = this.withInputs(nestedPlaceholder1, generator);

      return DAG.Prepared
          .withPlaceholder(nestedPlaceholder1)
          .withOutputs(new Value0FromTuple<>(dag), new Value1FromTuple<>(dag), new Value2FromTuple<>(dag),
              new Value3FromTuple<>(dag), new Value4FromTuple<>(dag), new Value5FromTuple<>(dag),
              new Value6FromTuple<>(dag)).withExecutor(_executor).withInput(getInput1());
    }

    /**
     * Applies the DAG to the provided input data (examples), running the model on the data and producing the results as a
     * {@link Result} instance.
     *
     * The inputs to this method take the form of parallel sequences of values.  Each sequence will provide the values
     * for a particular {@link com.linkedin.dagli.placeholder.Placeholder} in the DAG (the ordering of the
     * {@link com.linkedin.dagli.placeholder.Placeholder}s in a DAG is determined by their
     * ordering in the call to "DAG.withPlaceholders(...)...." that created it).  They are "parallel" because, e.g. all the
     * values corresponding to the fourth example will be the fourth value in their respective input sequences (all
     * sequences must, of course, be equal in size).
     *
     * @param values1 the sequence of values for the first
     * {@link com.linkedin.dagli.placeholder.Placeholder} in the DAG.
     * @param values2 the sequence of values for the second
     * {@link com.linkedin.dagli.placeholder.Placeholder} in the DAG.
     * @return a {@link Result} instance containing the results of applying the DAG on the provided data
     */
    public Result<RA, RB, RC, RD, RE, RF, RG> applyAll(Iterable<? extends A> values1, Iterable<? extends B> values2) {
      return new Result<RA, RB, RC, RD, RE, RF, RG>(_executor.internalAPI().applyUnsafe(this,
          new ObjectReader[] { ObjectReader.wrap(values1), ObjectReader.wrap(values2) }));
    }

    @Override
    public Tuple7<RA, RB, RC, RD, RE, RF, RG> apply(A value1, B value2) {
      try (
          AutoCloseableArray<ObjectReader<?>> res =
              new AutoCloseableArray<>(_executor.internalAPI().applyUnsafe(this,
                  new ObjectReader[] { ObjectReader.singleton(value1), ObjectReader.singleton(value2) }));

          ObjectIterator<?> resultIterator0 = res.get(0).iterator();
          ObjectIterator<?> resultIterator1 = res.get(1).iterator();
          ObjectIterator<?> resultIterator2 = res.get(2).iterator();
          ObjectIterator<?> resultIterator3 = res.get(3).iterator();
          ObjectIterator<?> resultIterator4 = res.get(4).iterator();
          ObjectIterator<?> resultIterator5 = res.get(5).iterator();
          ObjectIterator<?> resultIterator6 = res.get(6).iterator()) {
        return (Tuple7<RA, RB, RC, RD, RE, RF, RG>) Tuple7.of(resultIterator0.next(), resultIterator1.next(),
            resultIterator2.next(), resultIterator3.next(), resultIterator4.next(), resultIterator5.next(),
            resultIterator6.next());
      }
    }

    /**
     * Contains the results of applying the DAG to one or more examples, as produced by a call to applyAll(...).
     *
     * @param <RA> the type of the first result
     * @param <RB> the type of the second result
     * @param <RC> the type of the third result
     * @param <RD> the type of the fourth result
     * @param <RE> the type of the fifth result
     * @param <RF> the type of the sixth result
     * @param <RG> the type of the seventh result
     */
    public static final class Result<RA, RB, RC, RD, RE, RF, RG> extends AbstractDAGResult7<RA, RB, RC, RD, RE, RF, RG> {
      /**
       * Creates a new instance.
       *
       * @param results an array of {@link ObjectReader}s that contain each resultant output value of applying the DAG to
       *                the provided examples
       */
      Result(ObjectReader<?>[] results) {
        super(results);
      }
    }
  }
}
