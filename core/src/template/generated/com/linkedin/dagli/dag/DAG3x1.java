// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the dag/DAGXxY.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.dag;

import java.util.Collection;
import java.util.Collections;

import com.linkedin.dagli.annotation.equality.IgnoredByValueEquality;
import com.linkedin.dagli.annotation.equality.ValueEquality;
import com.linkedin.dagli.objectio.ObjectIterator;
import com.linkedin.dagli.objectio.ObjectReader;
import com.linkedin.dagli.preparer.Preparer3;
import com.linkedin.dagli.preparer.AbstractBatchPreparer3;
import com.linkedin.dagli.preparer.PreparerContext;
import com.linkedin.dagli.preparer.PreparerResultMixed;
import com.linkedin.dagli.producer.Producer;
import com.linkedin.dagli.reducer.Reducer;
import com.linkedin.dagli.transformer.AbstractPreparableTransformer3;
import com.linkedin.dagli.transformer.AbstractPreparedTransformer3;
import com.linkedin.dagli.transformer.PreparedTransformer3;
import com.linkedin.dagli.util.array.AutoCloseableArray;

import com.linkedin.dagli.generator.Generator;
import com.linkedin.dagli.placeholder.Placeholder;

import com.linkedin.dagli.tuple.Tuple3;


/**
 * DAGs, directed acyclic graphs, contain root nodes ({@link com.linkedin.dagli.placeholder.Placeholder}s and {@link com.linkedin.dagli.generator.Generator}s) and child nodes
 * ({@link com.linkedin.dagli.transformer.PreparedTransformer}s,
 * {@link com.linkedin.dagli.transformer.PreparableTransformer}s, and {@link com.linkedin.dagli.view.TransformerView}s).
 *
 * DAG3x1 is a <i>preparable</i> DAG.
 *
 * Before it can be used to transform new examples, it must be prepared with training data (e.g. by calling the
 * prepare() method).  The prepareAndApply() method both trains the DAG and applies it to the training data, yielding
 * both the prepared DAG and the results of applying the DAG to the training data (this can be useful in autoevaluation,
 * e.g. how well a classification model performs when predicting labels for its own training data).
 *
 * DAG3x1 is also a {@link com.linkedin.dagli.transformer.PreparableTransformer} and can be used as
 * a transformer within other DAGs.
 *
 * @param <A> the type of the first input
 * @param <B> the type of the second input
 * @param <C> the type of the third input
 * @param <RA> the type of the first result
 */
@ValueEquality
public class DAG3x1<A, B, C, RA> extends
    AbstractPreparableTransformer3<A, B, C, RA, DAG3x1.Prepared<A, B, C, RA>, DAG3x1<A, B, C, RA>> implements
    PreparableDAGTransformer<RA, DAG3x1.Prepared<A, B, C, RA>, DAG3x1<A, B, C, RA>> {

  private static final long serialVersionUID = 1;

  // the DAGStructure stores the actual graph
  private DAGStructure<RA> _dag;

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
   * @param input3 the {@link Producer} providing the third input to the transformer
   */
  DAG3x1(DAGStructure<RA> dag, Producer<? extends A> input1, Producer<? extends B> input2, Producer<? extends C> input3) {
    super(input1, input2, input3);
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
  public DAG3x1<A, B, C, RA> withReduction(Reducer.Level level) {
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

  public class InternalAPI extends
      AbstractPreparableTransformer3<A, B, C, RA, DAG3x1.Prepared<A, B, C, RA>, DAG3x1<A, B, C, RA>>.InternalAPI
      implements PreparableDAGTransformer.InternalAPI<RA, DAG3x1.Prepared<A, B, C, RA>, DAG3x1<A, B, C, RA>> {
    @Override
    public DAGStructure<RA> getDAGStructure() {
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
    public DAG3x1<A, B, C, RA> getInstance() {
      return DAG3x1.this;
    }
  }

  @Override
  protected Collection<? extends Reducer<? super DAG3x1<A, B, C, RA>>> getGraphReducers() {
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
  public DAG3x1<A, B, C, RA> withExecutor(DAGExecutor executor) {
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
   * @param input3 the {@link Producer} providing the third input to the transformer
   * @return a copy of this instance that will have the specified parents
   */
  public DAG3x1<A, B, C, RA> withInputs(Producer<? extends A> input1, Producer<? extends B> input2,
      Producer<? extends C> input3) {
    return super.withAllInputs(input1, input2, input3);
  }

  /**
   * Creates a new DAG that invokes this transformer, except that the first input is replaced by the specified {@link Generator}.
   * The resultant DAG will thus have an arity of 2 (one less than this transformer), and values that were formerly
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
  public DAG2x1<B, C, RA> withGeneratorAsInput1(Generator<A> generator) {
    Placeholder<B> nestedPlaceholder2 = new Placeholder<>("Original Input 2");
    Placeholder<C> nestedPlaceholder3 = new Placeholder<>("Original Input 3");
    DAG3x1<A, B, C, RA> dag = this.withInputs(generator, nestedPlaceholder2, nestedPlaceholder3);

    return DAG.withPlaceholders(nestedPlaceholder2, nestedPlaceholder3).withOutput(dag).withExecutor(_executor)
        .withInputs(getInput2(), getInput3());
  }

  /**
   * Creates a new DAG that invokes this transformer, except that the second input is replaced by the specified {@link Generator}.
   * The resultant DAG will thus have an arity of 2 (one less than this transformer), and values that were formerly
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
  public DAG2x1<A, C, RA> withGeneratorAsInput2(Generator<B> generator) {
    Placeholder<A> nestedPlaceholder1 = new Placeholder<>("Original Input 1");
    Placeholder<C> nestedPlaceholder3 = new Placeholder<>("Original Input 3");
    DAG3x1<A, B, C, RA> dag = this.withInputs(nestedPlaceholder1, generator, nestedPlaceholder3);

    return DAG.withPlaceholders(nestedPlaceholder1, nestedPlaceholder3).withOutput(dag).withExecutor(_executor)
        .withInputs(getInput1(), getInput3());
  }

  /**
   * Creates a new DAG that invokes this transformer, except that the third input is replaced by the specified {@link Generator}.
   * The resultant DAG will thus have an arity of 2 (one less than this transformer), and values that were formerly
   * provided as the third input to the transformer will now instead be generated by the {@link Generator}.
   *
   * This method is useful for eliding inputs to a transformer.  For example, a prepared classifier might have a
   * "vestigial" label input (inherited from a corresponding preparable classifier from which it was obtained) that can
   * be replaced by <code>Constant.nullValue()</code>.  Since the label input isn't used by the
   * prepared classifier (instead, it predicts a label from its other inputs and produces this as its result) replacing
   * it with a null has no effect and eliminates an unnecessary input.
   *
   * @param generator the generator that will provide a value for the third input
   * @return a new DAG that invokes this transformer with the same inputs, except the third, whose value is replaced by
   *         the produced by the provided generator
   */
  public DAG2x1<A, B, RA> withGeneratorAsInput3(Generator<C> generator) {
    Placeholder<A> nestedPlaceholder1 = new Placeholder<>("Original Input 1");
    Placeholder<B> nestedPlaceholder2 = new Placeholder<>("Original Input 2");
    DAG3x1<A, B, C, RA> dag = this.withInputs(nestedPlaceholder1, nestedPlaceholder2, generator);

    return DAG.withPlaceholders(nestedPlaceholder1, nestedPlaceholder2).withOutput(dag).withExecutor(_executor)
        .withInputs(getInput1(), getInput2());
  }

  /**
   * The {@link com.linkedin.dagli.preparer.Preparer} that prepares (trains) the DAG.
   *
   * @param <A> the type of the first input
   * @param <B> the type of the second input
   * @param <C> the type of the third input
   * @param <RA> the type of the first result
   */
  private static class Preparer<A, B, C, RA> extends AbstractBatchPreparer3<A, B, C, RA, DAG3x1.Prepared<A, B, C, RA>> {
    // the DAG being prepared
    private final DAG3x1<A, B, C, RA> _dag;

    /**
     * Creates a new instance.
     *
     * @param dag the DAG that will be prepared
     */
    Preparer(DAG3x1<A, B, C, RA> dag) {
      _dag = dag;
    }

    @Override
    @SuppressWarnings("unchecked")
    // we know the returned type is correct by the semantics of executors
    public PreparerResultMixed<PreparedTransformer3<A, B, C, RA>, DAG3x1.Prepared<A, B, C, RA>> finish(
        ObjectReader<Tuple3<A, B, C>> inputs) {
      return (PreparerResultMixed) _dag._executor.internalAPI().prepareUnsafe(_dag,
          ObjectReader.split(3, inputs.lazyMap(tuple -> tuple.toArray())));
    }

    @Override
    public void process(A value1, B value2, C value3) {
      // noop
    }
  }

  @Override
  protected Preparer3<A, B, C, RA, DAG3x1.Prepared<A, B, C, RA>> getPreparer(PreparerContext context) {
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
   * @param values3 the sequence of values for the third
   * {@link com.linkedin.dagli.placeholder.Placeholder} in the DAG.
   * @return a {@link Result} instance containing both the prepared DAG and the result of applying the DAG on the
   *         preparation data
   */
  public Result<A, B, C, RA> prepareAndApply(Iterable<? extends A> values1, Iterable<? extends B> values2,
      Iterable<? extends C> values3) {
    DAGExecutionResult<RA, DAG3x1.Prepared<A, B, C, RA>> res =
        _executor.internalAPI().prepareAndApplyUnsafe(this,
            new ObjectReader[] { ObjectReader.wrap(values1), ObjectReader.wrap(values2), ObjectReader.wrap(values3) });
    return new Result<>((DAG3x1.Prepared<A, B, C, RA>) res.getPreparerResult().getPreparedTransformerForNewData(),
        res.getOutputs());
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
   * @param values3 the sequence of values for the third
   * {@link com.linkedin.dagli.placeholder.Placeholder} in the DAG.
   * @return a prepared (trained) DAG that can be applied to new examples (inference)
   */
  public DAG3x1.Prepared<A, B, C, RA> prepare(Iterable<? extends A> values1, Iterable<? extends B> values2,
      Iterable<? extends C> values3) {
    return (DAG3x1.Prepared<A, B, C, RA>) _executor
        .internalAPI()
        .prepareUnsafe(this,
            new ObjectReader[] { ObjectReader.wrap(values1), ObjectReader.wrap(values2), ObjectReader.wrap(values3) })
        .getPreparedTransformerForNewData();
  }

  /**
   * {@link Result}s are returned by the DAG's prepareAndApply(...) methods and contain both the prepared (trained) DAG
   * and the results of applying that DAG to the preparation (training) data.
   *
  * @param <A> the type of the first input
  * @param <B> the type of the second input
  * @param <C> the type of the third input
  * @param <RA> the type of the first result
   */
  public static final class Result<A, B, C, RA> extends AbstractDAGResult1<RA> {
    // the prepared DAG
    private final DAG3x1.Prepared<A, B, C, RA> _preparedDAG;

    /**
     * @return the {@link Prepared} DAG that was prepared (trained) on the provided preparation (training) data.
     */
    public DAG3x1.Prepared<A, B, C, RA> getPreparedDAG() {
      return _preparedDAG;
    }

    /**
     * Creates a new instance.
     *
     * @param preparedDAG the prepared DAG to be stored in this instance
     * @param results an array of {@link ObjectReader}s that contain each resultant output value of applying the DAG to
     *                the preparation (training) data
     */
    Result(DAG3x1.Prepared<A, B, C, RA> preparedDAG, ObjectReader<?>[] results) {
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
  * @param <C> the type of the third input
  * @param <RA> the type of the first result
   */
  @ValueEquality
  public static class Prepared<A, B, C, RA> extends
      AbstractPreparedTransformer3<A, B, C, RA, DAG3x1.Prepared<A, B, C, RA>> implements
      PreparedDAGTransformer<RA, DAG3x1.Prepared<A, B, C, RA>> {

    private static final long serialVersionUID = 1;

    // the DAGStructure stores the actual graph
    private DAGStructure<RA> _dag;

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
    * @param input3 the {@link Producer} providing the third input to the transformer
     */
    Prepared(DAGStructure<RA> dag, Producer<? extends A> input1, Producer<? extends B> input2,
        Producer<? extends C> input3) {
      super(input1, input2, input3);
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
    public DAG3x1.Prepared<A, B, C, RA> withReduction(Reducer.Level level) {
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

    public class InternalAPI extends
        AbstractPreparedTransformer3<A, B, C, RA, DAG3x1.Prepared<A, B, C, RA>>.InternalAPI implements
        PreparedDAGTransformer.InternalAPI<RA, DAG3x1.Prepared<A, B, C, RA>> {
      @Override
      public DAGStructure<RA> getDAGStructure() {
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
      public DAG3x1.Prepared<A, B, C, RA> getInstance() {
        return DAG3x1.Prepared.this;
      }
    }

    @Override
    protected Collection<? extends Reducer<? super DAG3x1.Prepared<A, B, C, RA>>> getGraphReducers() {
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
    public DAG3x1.Prepared<A, B, C, RA> withExecutor(PreparedDAGExecutor executor) {
      return clone(r -> r._executor = executor);
    }

    /**
     * Sets the parents of this transformer.  The results of the parent nodes will be the inputs to the transformer.
     *
     * @param input1 the {@link Producer} providing the first input to the transformer
     * @param input2 the {@link Producer} providing the second input to the transformer
     * @param input3 the {@link Producer} providing the third input to the transformer
     * @return a copy of this instance that will have the specified parents
     */
    public DAG3x1.Prepared<A, B, C, RA> withInputs(Producer<? extends A> input1, Producer<? extends B> input2,
        Producer<? extends C> input3) {
      return super.withAllInputs(input1, input2, input3);
    }

    /**
     * Creates a new DAG that invokes this transformer, except that the first input is replaced by the specified {@link Generator}.
     * The resultant DAG will thus have an arity of 2 (one less than this transformer), and values that were formerly
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
    public DAG2x1.Prepared<B, C, RA> withGeneratorAsInput1(Generator<A> generator) {
      Placeholder<B> nestedPlaceholder2 = new Placeholder<>("Original Input 2");
      Placeholder<C> nestedPlaceholder3 = new Placeholder<>("Original Input 3");
      DAG3x1.Prepared<A, B, C, RA> dag = this.withInputs(generator, nestedPlaceholder2, nestedPlaceholder3);

      return DAG.Prepared.withPlaceholders(nestedPlaceholder2, nestedPlaceholder3).withOutput(dag)
          .withExecutor(_executor).withInputs(getInput2(), getInput3());
    }

    /**
     * Creates a new DAG that invokes this transformer, except that the second input is replaced by the specified {@link Generator}.
     * The resultant DAG will thus have an arity of 2 (one less than this transformer), and values that were formerly
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
    public DAG2x1.Prepared<A, C, RA> withGeneratorAsInput2(Generator<B> generator) {
      Placeholder<A> nestedPlaceholder1 = new Placeholder<>("Original Input 1");
      Placeholder<C> nestedPlaceholder3 = new Placeholder<>("Original Input 3");
      DAG3x1.Prepared<A, B, C, RA> dag = this.withInputs(nestedPlaceholder1, generator, nestedPlaceholder3);

      return DAG.Prepared.withPlaceholders(nestedPlaceholder1, nestedPlaceholder3).withOutput(dag)
          .withExecutor(_executor).withInputs(getInput1(), getInput3());
    }

    /**
     * Creates a new DAG that invokes this transformer, except that the third input is replaced by the specified {@link Generator}.
     * The resultant DAG will thus have an arity of 2 (one less than this transformer), and values that were formerly
     * provided as the third input to the transformer will now instead be generated by the {@link Generator}.
     *
     * This method is useful for eliding inputs to a transformer.  For example, a prepared classifier might have a
     * "vestigial" label input (inherited from a corresponding preparable classifier from which it was obtained) that can
     * be replaced by <code>Constant.nullValue()</code>.  Since the label input isn't used by the
     * prepared classifier (instead, it predicts a label from its other inputs and produces this as its result) replacing
     * it with a null has no effect and eliminates an unnecessary input.
     *
     * @param generator the generator that will provide a value for the third input
     * @return a new DAG that invokes this transformer with the same inputs, except the third, whose value is replaced by
     *         the produced by the provided generator
     */
    public DAG2x1.Prepared<A, B, RA> withGeneratorAsInput3(Generator<C> generator) {
      Placeholder<A> nestedPlaceholder1 = new Placeholder<>("Original Input 1");
      Placeholder<B> nestedPlaceholder2 = new Placeholder<>("Original Input 2");
      DAG3x1.Prepared<A, B, C, RA> dag = this.withInputs(nestedPlaceholder1, nestedPlaceholder2, generator);

      return DAG.Prepared.withPlaceholders(nestedPlaceholder1, nestedPlaceholder2).withOutput(dag)
          .withExecutor(_executor).withInputs(getInput1(), getInput2());
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
     * @param values3 the sequence of values for the third
     * {@link com.linkedin.dagli.placeholder.Placeholder} in the DAG.
     * @return a {@link Result} instance containing the results of applying the DAG on the provided data
     */
    public Result<RA> applyAll(Iterable<? extends A> values1, Iterable<? extends B> values2,
        Iterable<? extends C> values3) {
      return new Result<RA>(_executor.internalAPI().applyUnsafe(this,
          new ObjectReader[] { ObjectReader.wrap(values1), ObjectReader.wrap(values2), ObjectReader.wrap(values3) }));
    }

    @Override
    public RA apply(A value1, B value2, C value3) {
      try (
          AutoCloseableArray<ObjectReader<?>> res =
              new AutoCloseableArray<>(_executor.internalAPI().applyUnsafe(
                  this,
                  new ObjectReader[] { ObjectReader.singleton(value1), ObjectReader.singleton(value2), ObjectReader
                      .singleton(value3) }));

          ObjectIterator<?> resultIterator0 = res.get(0).iterator()) {
        return (RA) resultIterator0.next();
      }
    }

    /**
     * Contains the results of applying the DAG to one or more examples, as produced by a call to applyAll(...).
     *
     * @param <RA> the type of the first result
     */
    public static final class Result<RA> extends AbstractDAGResult1<RA> {
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
