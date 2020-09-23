<#import "../common.ftl" as c />
<@c.AutoGeneratedWarning />
package com.linkedin.dagli.dag;

import com.linkedin.dagli.annotation.equality.IgnoredByValueEquality;
import com.linkedin.dagli.annotation.equality.ValueEquality;
import com.linkedin.dagli.handle.ProducerHandle;
import com.linkedin.dagli.objectio.ObjectIterator;
import com.linkedin.dagli.objectio.ObjectReader;
import com.linkedin.dagli.placeholder.Placeholder;
import com.linkedin.dagli.preparer.AbstractBatchPreparerDynamic;
import com.linkedin.dagli.preparer.PreparerContext;
import com.linkedin.dagli.preparer.PreparerDynamic;
import com.linkedin.dagli.preparer.PreparerResultMixed;
import com.linkedin.dagli.producer.MissingInput;
import com.linkedin.dagli.producer.Producer;
import com.linkedin.dagli.reducer.Reducer;
import com.linkedin.dagli.transformer.AbstractPreparableTransformerDynamic;
import com.linkedin.dagli.transformer.AbstractPreparedTransformerDynamic;
import com.linkedin.dagli.transformer.DynamicInputs;
import com.linkedin.dagli.transformer.PreparedTransformer;
import com.linkedin.dagli.tuple.Tuple;
<#list 2..c.maxArity as index>import com.linkedin.dagli.tuple.Tuple${index};
</#list>
import com.linkedin.dagli.util.invariant.Arguments;
import com.linkedin.dagli.util.collection.LinkedNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Objects;
import java.util.com.linkedin.dagli.util.stream.Collectors;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

<#macro Common prepared>
  <#assign dynamicDAGName>DynamicDAG<#if prepared>.Prepared</#if></#assign>
  private static final long serialVersionUID = 1;

  private DAGStructure<R> _dag = null;

  private List<Placeholder<?>> _placeholders = null;
  private List<Producer<?>> _outputs = null;

  // Keep track of the original output handles so users can refer to them later (the DAGStructure contains a map from
  // placeholders to their indices, so we dont need to store those ourselves)
  @IgnoredByValueEquality
  private Object2IntOpenHashMap<ProducerHandle<?>> _outputToIndexMap = null;

  // not ignored by value equality because _dag may not have been constructed yet
  private Reducer.Level _reductionLevel = Reducer.Level.NORMAL;

  @IgnoredByValueEquality
  private <#if prepared>Prepared</#if>DAGExecutor _executor = new LocalDAGExecutor();

  @Override
  public void validate() {
    super.validate();
    Objects.requireNonNull(_dag, "The DAG has not yet been set by calling withOutputs(...)");
  }

  /**
   * Returns a copy of this DAG that will use the specified {@link Producer} to provide values corresponding to the
   * given {@link Placeholder}.  This must be called after the DAG has been specified via {@code withOutputs(...)} and
   * is only relevant when the DAG is being used as a transformer within a larger DAG.
   *
   * @param placeholder the placeholder corresponding to the input to set
   * @param inputForPlaceholder the producer providing the values for the input
   * @param <T> the type of value required for the given input
   * @return a copy of this DAG that will accept the specified input
   */
  public <T> ${dynamicDAGName}<R> withInput(Placeholder<T> placeholder, Producer<? extends T> inputForPlaceholder) {
    int index = _dag.getNodeIndex(placeholder);
    Arguments.check(index >= 0, "Placeholder is not among the known placeholders for this DAG");
    return clone(c -> c._inputs.set(index, inputForPlaceholder));
  }

  /**
   * Returns a copy of this DAG that will use the specified {@link Producer} to provide values corresponding to the
   * given {@link Placeholder}.  This must be called after the DAG has been specified via {@code withOutputs(...)} and
   * is only relevant when the DAG is being used as a transformer within a larger DAG.
   *
   * @param placeholderAccessor the placeholder corresponding to the input to set
   * @param inputForPlaceholder the producer providing the values for the input
   * @param <T> the type of value required for the given input
   * @return a copy of this DAG that will accept the specified input
   */
  public <T> ${dynamicDAGName}<R> withInput(DynamicInputs.Accessor<T> placeholderAccessor,
      Producer<? extends T> inputForPlaceholder) {
    return clone(c -> c._inputs.set(placeholderAccessor.getIndex(), inputForPlaceholder));
  }

  /**
   * Creates an {@link DynamicInputs.Accessor} that may be used to refer to a particular placeholder when providing
   * input values to the DAG.  Using accessors is faster than using {@link Producer}s directly because it avoids
   * repeated hashtable lookups, but only if the accessor is used more than once.
   *
   * @param placeholder the placeholder whose accessor should be retrieved
   * @param <T> the type of value produced by the placeholder
   * @return an accessor for the given placeholder, or null if the placeholder is not known to this DAG
   */
  public <T> DynamicInputs.Accessor<T> getPlaceholderAccessor(Placeholder<T> placeholder) {
    int index = _dag.getNodeIndex(placeholder);
    return index < 0 ? null : new DynamicInputs.Accessor<>(index);
  }

  @SuppressWarnings("unchecked")
  private void setOutputs(List<Producer<?>> outputs) {
    _outputs = outputs;
    if (_placeholders == null) {
      _placeholders = LinkedNode.filterByClass(Producer.subgraphProducers(outputs), (Class<Placeholder<?>>) (Class) Placeholder.class)
        .map(LinkedNode::getItem).distinct().collect(Collectors.toList());
    }
    setDAG();
  }

  private void setPlaceholders(List<Placeholder<?>> placeholders) {
    _placeholders = placeholders;
    if (_outputs != null) {
      setDAG();
    }
  }

  private void setDAG() {
    _outputToIndexMap = new Object2IntOpenHashMap<>(_outputs.size());
    _outputs.com.linkedin.dagli.util.stream().map(Producer::handle).forEach(handle -> _outputToIndexMap.put(handle, _outputToIndexMap.size()));

    _dag = new DAGStructure<>(DAGReducer.reduce(new DeduplicatedDAG(_placeholders, _outputs), _reductionLevel));
    this._inputs = new ArrayList<>(MissingInput.producerList(_placeholders.size()));
    <#if prepared>Arguments.check(_dag._isPrepared, "Cannot create prepared DAG containing a preparable transformer");</#if>
  }

  /**
   * Returns a copy of this DAG that will use the specified placeholders.
   *
   * It is more efficient to call this method prior to {@code withOutputs(...)}.
   *
   * @param placeholders the placeholders to use for the DAG
   * @return a copy of this DAG that will use the specified placeholders
   */
  public ${dynamicDAGName}<R> withPlaceholders(Placeholder<?>... placeholders) {
    return withPlaceholders(Arrays.asList(placeholders));
  }

  /**
   * Returns a copy of this DAG that will use the specified placeholders.
   *
   * It is more efficient to call this method prior to {@code withOutputs(...)}.
   *
   * @param placeholders the placeholders to use for the DAG
   * @return a copy of this DAG that will use the specified placeholders
   */
  public ${dynamicDAGName}<R> withPlaceholders(List<? extends Placeholder<?>> placeholders) {
    return clone(c -> c.setPlaceholders(new ArrayList<>(placeholders)));
  }

  /**
   * Returns a copy of this DAG that will have the given ordered sequence of outputs.  The placeholders comprising the
   * inputs to the new DAG will be automatically discovered unless specified via {@code withPlaceholders(...)}.
   *
   * @param outputs the outputs of this DAG (at most 10)
   * @return a copy of this DAG that will produce the requested outputs
   * @throws IllegalArgumentException if more than 10 outputs are specified
   */
  public ${dynamicDAGName}<?> withOutputs(Producer<?>... outputs) {
    return withOutputs(Arrays.asList(outputs));
  }

  /**
   * Returns a copy of this DAG that will have the given ordered sequence of outputs.  The placeholders comprising the
   * inputs to the new DAG will be automatically discovered unless specified via {@code withPlaceholders(...)}.
   *
   * @param outputs the outputs of this DAG (at most 10)
   * @return a copy of this DAG that will produce the requested outputs
   * @throws IllegalArgumentException if more than 10 outputs are specified
   */
  public ${dynamicDAGName}<?> withOutputs(Collection<? extends Producer<?>> outputs) {
    Arguments.check(outputs.size() <= 10, "A DAG cannot have more than 10 outputs; consider aggregating "
        + "some of the outputs into a data structure like a @Struct or List");
    return clone(c -> c.setOutputs(new ArrayList<>(outputs)));
  }

  <#list 1..c.maxArity as arity>
  /**
   * Returns a copy of this DAG that will have the given ordered sequence of outputs.  The placeholders comprising the
   * inputs to the new DAG will be automatically discovered unless specified via {@code withPlaceholders(...)}.
   *
<#list 1..arity as index>   * @param output${index} the ${c.positionNames[index]} output of the DAG
</#list>
<@c.Indent 1><@c.GenericResultTypesJavadoc arity /></@c.Indent>
   * @return a copy of this DAG that will produce the requested outputs
   * @throws IllegalArgumentException if more than 10 outputs are specified
   */
  @SuppressWarnings("unchecked")
  public <<@c.ResultGenericArguments arity />> ${dynamicDAGName}<<@c.ResultTuple arity />> withOutputs(<#list 1..arity as index>Producer<${c.ResultGenericArgument(index)}> output${index}<#sep>, </#list>) {
    return (${dynamicDAGName}<<@c.ResultTuple arity />>) withOutputs(Arrays.asList(<#list 1..arity as index>output${index}<#sep>, </#list>));
  }
  </#list>

  @Override
  public ${dynamicDAGName}<R> withExecutor(<#if prepared>Prepared</#if>DAGExecutor executor) {
    return clone(r -> r._executor = Objects.requireNonNull(executor));
  }

  /**
   * @return a copy of this DAG that, the next time the DAG is fully specified (by setting the outputs or by setting
   *         the placeholders when the outputs are already set), will not apply reductions to its encapsulated
   *         elements (note that this does not affect the reduction of this DAG within another, encapsulating DAG)
   */
  public ${dynamicDAGName}<R> withNoReduction() {
    return withReduction(null);
  }

  @Override
  public ${dynamicDAGName}<R> withReduction(Reducer.Level level) {
    if (_dag == null || Reducer.Level.compare(level, _reductionLevel) >= 0) {
      return level == _reductionLevel ? this : clone(c -> c._reductionLevel = level);
    }

    return clone(c -> {
      c._dag = new DAGStructure<>(DAGReducer.reduce(new DeduplicatedDAG(_dag), level));
      c._reductionLevel = level;
    });
  }

  @Override
  protected Collection<? extends Reducer<? super ${dynamicDAGName}<R>>> getGraphReducers() {
    return Collections.singletonList(DAGTransformerReducer.INSTANCE);
  }

  @Override
  protected boolean hasAlwaysConstantResult() {
    return _dag._isAlwaysConstant;
  }

  @Override
  public InternalAPI internalAPI() {
    return new InternalAPI();
  }

  public class InternalAPI
      extends Abstract<#if prepared>Prepared<#else>Preparable</#if>TransformerDynamic<R, <#if !prepared>Prepared<R>, </#if>${dynamicDAGName}<R>>.InternalAPI
      implements <#if prepared>Prepared<#else>Preparable</#if>DAGTransformer.InternalAPI<R, <#if !prepared>Prepared<R>, </#if>${dynamicDAGName}<R>> {
    @Override
    public DAGStructure<R> getDAGStructure() {
      return _dag;
    }

    @Override
    public Reducer.Level getReductionLevel() {
      return _reductionLevel;
    }

    @Override
    public <#if prepared>Prepared</#if>DAGExecutor getDAGExecutor() {
      return _executor;
    }

    @Override
    public ${dynamicDAGName}<R> getInstance() {
      return ${dynamicDAGName}.this;
    }
  }
</#macro>

/**
 * A DynamicDAG is similar to DAGs of fixed arity, such as {@link DAG2x3}, but allows for any number of inputs to the
 * DAG (while still limiting the number of outputs to a maximum of 10).
 *
 * DynamicDAGs are rarely required: usually, a DAG with a high number of inputs (more than 2 or 3) should aggregate
 * their input values into @Structs or other data structure that can provide well-documented, user-friendly inputs
 * to the DAG.
 *
 * The utility for DynamicDAGs is thus:
 * (1) When you need a larger number of inputs but can't be bothered to create a @Struct (or similar); DynamicDAG
 *     may be preferable to the fixed-arity DAGs in these cases because it required "named", rather than ordinal,
 *     inputs, which greatly reduces the chances of accidentally transposing two inputs of the same type and creating
 *     logic bugs.
 * (2) When automatically generating graphs that may have a large number of inputs (e.g. when generating subgraphs from
 *     an existing graph).
 */
@ValueEquality
public class DynamicDAG<R> extends AbstractPreparableTransformerDynamic<R, DynamicDAG.Prepared<R>, DynamicDAG<R>> implements PreparableDAGTransformer<R, DynamicDAG.Prepared<R>, DynamicDAG<R>> {
  <@Common false />

  @Override
  protected boolean hasIdempotentPreparer() {
    return _dag._hasIdempotentPreparer;
  }

  private abstract class AbstractPrepareBuilder<S extends AbstractPrepareBuilder<S>> {
    @SuppressWarnings("unchecked")
    final ObjectReader<Object>[] _arguments = new ObjectReader[_dag._placeholders.size()];

    @SuppressWarnings("unchecked")
    public <T> S input(Placeholder<T> placeholder, Iterable<? extends T> value) {
      int index = _dag.getNodeIndex(placeholder);
      Arguments.check(index >= 0, "Placeholder is not among the known placeholders for this DAG");
      _arguments[index] = ObjectReader.wrap(value);
     return (S) this;
    }

    @SuppressWarnings("unchecked")
    public <T> S input(DynamicInputs.Accessor<T> placeholderAccessor, Iterable<? extends T> value) {
      _arguments[placeholderAccessor.getIndex()] = ObjectReader.wrap(value);
      return (S) this;
    }
  }

  public class PrepareBuilder extends AbstractPrepareBuilder<PrepareBuilder> {
    public DynamicDAG.Prepared<R> execute() {
      for (int i = 0; i < _arguments.length; i++) {
        Arguments.check(_arguments[i] != null, "Input #" + (i + 1) + " has not been set");
      }

      DAGExecutionResult<R, Prepared<R>> res =
          _executor.internalAPI().prepareAndApplyUnsafe(DynamicDAG.this, _arguments);
      return res.getPreparerResult().getPreparedTransformerForNewData();
    }
  }


  public class PrepareAndApplyBuilder extends AbstractPrepareBuilder<PrepareAndApplyBuilder> {
    public DynamicDAGResult<R> execute() {
      for (int i = 0; i < _arguments.length; i++) {
        Arguments.check(_arguments[i] != null, "Input #" + (i + 1) + " has not been set");
      }

      DAGExecutionResult<R, Prepared<R>> res =
          _executor.internalAPI().prepareAndApplyUnsafe(DynamicDAG.this, _arguments);
      return new DynamicDAGResult<R>(res.getPreparerResult().getPreparedTransformerForNewData(), res.getOutputs(),
          _outputToIndexMap);
    }
  }

  public PrepareBuilder prepare() {
    return new PrepareBuilder();
  }

  public PrepareAndApplyBuilder prepareAndApply() {
    return new PrepareAndApplyBuilder();
  }

  /**
   * Creates a "minimal subgraph" DAG from the provided {@code leaf} producer and a set of producers which must
   * be direct inputs to the resulting DAG.
   *
   * A minimal subgraph is defined as a subgraph containing {@code leaf} and as few of its ancestors as possible such
   * that any ancestor of {@code leaf} that is also a descendant of one of the {@code requiredDirectInputs} will be
   * included in the subgraph.
   *
   * Each of the {@code requiredDirectInputs} will then be an input to the DAG encapsulating the subgraph, even if that
   * producer is not actually an ancestor of {@code leaf} (it will simply be an ignored input to the DAG in this case).
   * Specifically, these will be the first inputs to the DAG, in the order they are provided.
   *
   * @param leaf the producer that is the leaf of the subgraph and provides the (sole) result of the returned DAG
   * @param requiredDirectInputs up to nine producers that will be among the inputs to the returned DAG
   * @param <R> the type of result of {@code leaf}
   * @return a DAG with a single output encapsulating a minimal bounded subgraph
   */
  public static <R> DynamicDAG<R> fromMinimalInputBoundedSubgraph(Producer<R> leaf,
      Producer<?>... requiredDirectInputs) {
    return (DynamicDAG<R>) fromMinimalInputBoundedSubgraph(false, leaf, requiredDirectInputs);
  }

  private static <R> DAGTransformer<R, ?> fromMinimalInputBoundedSubgraph(boolean prepared, Producer<R> leaf,
      Producer<?>... requiredDirectInputs) {
    HashSet<Producer<?>> outOfBounds = new HashSet<>();
    List<Producer<?>> requiredDirectInputsList = Arrays.asList(requiredDirectInputs);
    HashSet<Producer<?>> boundingAncestors = new HashSet<>(requiredDirectInputsList);

    // find all nodes in the graph that are "out of bounds" as defined by our bounding ancestors
    DAGUtil.findOutOfBounds(leaf, outOfBounds, boundingAncestors);

    // we also consider the bounding ancestors out of bounds
    outOfBounds.addAll(boundingAncestors);

    // find the minimal inputs required for our bounded subgraph
    HashSet<Producer<?>> minimalInputs = new HashSet<>();
    DAGUtil.findMinimalInputs(leaf, minimalInputs, outOfBounds);

    minimalInputs.addAll(requiredDirectInputsList);
    IdentityHashMap<Producer<?>, Producer<?>> replacementMap = new IdentityHashMap<>();
    for (Producer<?> input : minimalInputs) {
      replacementMap.put(input, input instanceof Placeholder ? input : new Placeholder<>());
    }

    Producer<R> newLeaf = DAGUtil.replaceInputs(leaf, replacementMap);
    minimalInputs.removeAll(requiredDirectInputsList);
    ArrayList<Producer<?>> inputList = new ArrayList<>(requiredDirectInputsList.size() + minimalInputs.size());
    inputList.addAll(requiredDirectInputsList);
    inputList.addAll(minimalInputs);

    List<Placeholder<?>> placeholders = inputList.com.linkedin.dagli.util.stream()
        .map(replacementMap::get)
        .map(producer -> (Placeholder<?>) producer)
        .collect(Collectors.toList());

    return prepared ? new DynamicDAG.Prepared<>().withPlaceholders(placeholders)
        .withOutputs(newLeaf)
        .internalAPI()
        .withInputsUnsafe(inputList) : new DynamicDAG<>().withPlaceholders(placeholders)
        .withOutputs(newLeaf)
        .internalAPI()
        .withInputsUnsafe(inputList);
  }

  @Override
  protected PreparerDynamic<R, Prepared<R>> getPreparer(PreparerContext context) {
    return new Preparer();
  }

  private class Preparer extends AbstractBatchPreparerDynamic<R, Prepared<R>> {
    @Override
    public void processUnsafe(Object[] values) { }

    @Override
    public PreparerResultMixed<? extends PreparedTransformer<? extends R>, Prepared<R>> finishUnsafe(
        ObjectReader<Object[]> inputs) {
      // using split inputs like this is (possibly) inefficient, but it's not really important since in the real world
      // a DAG embedded within another DAG (the only time this Preparer would be used) will be reduced away anyway, and
      // this method will never be called (unless reductions are disabled)
      return _executor.internalAPI().prepareUnsafe(DynamicDAG.this, ObjectReader.split(_dag._placeholders.size(), inputs));
    }
  }

  @ValueEquality
  public static class Prepared<R> extends AbstractPreparedTransformerDynamic<R, Prepared<R>> implements PreparedDAGTransformer<R, Prepared<R>> {
    <@Common true />

    /**
     * Creates a new DAG; a graph must be provided via {@code withOutputs(...)} before the DAG will be valid.
     */
    public Prepared() { }

    Prepared(DynamicDAG<?> progenitor, List<? extends Placeholder<?>> placeholders, List<? extends Producer<?>> outputs) {
      _placeholders = new ArrayList<>(placeholders);
      _outputs = new ArrayList<>(outputs);
      setDAG();
      _outputToIndexMap = progenitor._outputToIndexMap;
    }

    @Override
    protected R apply(List<?> values) {
      ApplyBuilder builder = new ApplyBuilder();
      for (int i = 0; i < builder._arguments.length; i++) {
        builder._arguments[i] = ObjectReader.singleton(values.get(i));
      }
      return builder.execute();
    }

    /**
     * @return a builder for specifying the input values and executing the DAG
     */
    public ApplyBuilder apply() {
      return new ApplyBuilder();
    }

    public class ApplyBuilder {
      @SuppressWarnings("unchecked")
      final ObjectReader<Object>[] _arguments = new ObjectReader[_dag._placeholders.size()];

      public <T> ApplyBuilder input(Placeholder<T> placeholder, T value) {
        int index = _dag.getNodeIndex(placeholder);
        Arguments.check(index >= 0, "Placeholder is not among the known placeholders for this DAG");
        _arguments[index] = ObjectReader.singleton(value);
        return this;
      }

      public <T> ApplyBuilder input(DynamicInputs.Accessor<T> placeholderAccessor, T value) {
        _arguments[placeholderAccessor.getIndex()] = ObjectReader.singleton(value);
        return this;
      }

      @SuppressWarnings("unchecked")
      public R execute() {
        for (int i = 0; i < _arguments.length; i++) {
          Arguments.check(_arguments[i] != null, "Input #" + (i + 1) + " has not been set");
        }

        ObjectReader<?>[] res = _executor.internalAPI().applyUnsafe(DynamicDAG.Prepared.this, _arguments);

        if (_dag._outputs.size() == 1) {
          try (ObjectReader<?> reader = res[0]; ObjectIterator<?> iterator = reader.iterator()) {
            return (R) iterator.next();
          }
        }

        Object[] arr = new Object[_dag._outputs.size()];

        for (int i = 0; i < arr.length; i++) {
          try (ObjectReader<?> reader = res[i]; ObjectIterator<?> iterator = reader.iterator()) {
            arr[i] = iterator.next();
          }
        }

        return (R) Tuple.generator(arr.length).fromArray(arr);
      }
    }

    /**
     * @return a builder for specifying the input values and executing the DAG
     */
    public ApplyAllBuilder applyAll() {
      return new ApplyAllBuilder();
    }

    public class ApplyAllBuilder {
      @SuppressWarnings("unchecked")
      final ObjectReader<Object>[] _arguments = new ObjectReader[_dag._placeholders.size()];

      public <T> ApplyAllBuilder input(Placeholder<T> placeholder, Iterable<? extends T> value) {
        int index = _dag.getNodeIndex(placeholder);
        Arguments.check(index >= 0, "Placeholder is not among the known placeholders for this DAG");
        _arguments[index] = ObjectReader.wrap(value);
        return this;
      }

      public <T> ApplyAllBuilder input(DynamicInputs.Accessor<T> placeholder, Iterable<? extends T> value) {
        _arguments[placeholder.getIndex()] = ObjectReader.wrap(value);
        return this;
      }

      public DynamicDAGResult.Prepared<R> execute() {
        for (int i = 0; i < _arguments.length; i++) {
          Arguments.check(_arguments[i] != null, "Input #" + (i + 1) + " has not been set");
        }

        ObjectReader<?>[] res = _executor.internalAPI().applyUnsafe(DynamicDAG.Prepared.this, _arguments);
        return new DynamicDAGResult.Prepared<>(res, _outputToIndexMap);
      }
    }

    /**
     * Creates a "minimal subgraph" DAG from the provided {@code leaf} producer and a set of producers which must
     * be direct inputs to the resulting DAG.
     *
     * A minimal subgraph is defined as a subgraph containing {@code leaf} and as few of its ancestors as possible such
     * that any ancestor of {@code leaf} that is also a descendant of one of the {@code requiredDirectInputs} will be
     * included in the subgraph.
     *
     * Each of the {@code requiredDirectInputs} will then be an input to the DAG encapsulating the subgraph, even if that
     * producer is not actually an ancestor of {@code leaf} (it will simply be an ignored input to the DAG in this case).
     * Specifically, these will be the first inputs to the DAG, in the order they are provided.
     *
     * Finally, the minimal subgraph must be <i>prepared</i>, containing no preparable transformers.
     *
     * @param leaf the producer that is the leaf of the subgraph and provides the (sole) result of the returned DAG
     * @param requiredDirectInputs up to nine producers that will be among the inputs to the returned DAG
     * @param <R> the type of result of {@code leaf}
     * @return a DAG with a single output encapsulating a minimal bounded subgraph
     */
    @SuppressWarnings("unchecked")
    public static <R> DynamicDAG.Prepared<R> fromMinimalInputBoundedSubgraph(Producer<R> leaf,
        Producer<?>... requiredDirectInputs) {
      return (DynamicDAG.Prepared<R>) DynamicDAG.fromMinimalInputBoundedSubgraph(true, leaf, requiredDirectInputs);
    }
  }
}
