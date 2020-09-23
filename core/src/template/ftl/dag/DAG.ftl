<#import "../common.ftl" as c />
<@c.AutoGeneratedWarning />
package com.linkedin.dagli.dag;
import com.linkedin.dagli.placeholder.Placeholder;
import com.linkedin.dagli.producer.Producer;

<#macro OutputProducerArguments count name="output">
  <@compress single_line=true>
    <#list 1..count as index>Producer<? extends ${c.ResultGenericArgument(index)}> ${name}${c.InputSuffix(index)}<#sep>, </#list>
  </@compress>
</#macro>

<#macro WithPlaceholders placeholderArity>
  <@compress single_line=true>
  WithPlaceholders${placeholderArity}<<@c.InputGenericArguments placeholderArity />>
  </@compress>
</#macro>

<#macro WithOutputs placeholderArity outputArity>
  <@compress single_line=true>
  WithOutputs${placeholderArity}x${outputArity}<<@c.InputGenericArguments placeholderArity />, <@c.ResultGenericArguments outputArity />>
  </@compress>
</#macro>

<#macro WithPlaceholdersMethod isPrepared arity>
/**
 * Creates a (partial) DAG with the provided {@link Placeholder}s.  The partial DAG will become a completed DAG once the
 * withOutputs(...) method is then called on it to specify the outputs.
 *
 * The ancestry of your output nodes must descend (only) from the {@link Placeholder}s provided to this method and from
 * Generators.  If any output node has another {@link Placeholder} node as an ancestor, an exception will be thrown.
 *
 * As an alternative to this method, you can instead use withInputs(...) to create a DAG object that captures a
 * "subgraph" that is not necessarily rooted on your graph's {@link Placeholder}s.
 *
<#list 1..arity as index> * @param placeholder${c.InputSuffix(index)} placeholder ${c.InputSuffix(index)} of the DAG
 * @param <${c.InputGenericArgument(index)}> the type of placeholder ${c.InputSuffix(index)}
</#list>
 * @return a partial DAG rooted at the provided placeholders
 */
public static <<@c.InputGenericArguments arity />> PartialDAG.<#if isPrepared>Prepared.</#if><@WithPlaceholders arity /> withPlaceholder<@c.s arity />(<@c.ValuesArguments "Placeholder" arity "placeholder" />) {
  return new PartialDAG.<#if isPrepared>Prepared.</#if><@WithPlaceholders arity />(<#list 1..arity as index>placeholder${c.InputSuffix(index)}<#sep>, </#list>);
}
</#macro>

<#macro WithPlaceholdersMethods isPrepared>
  <#list 1..c.maxArity as arity>
    <@WithPlaceholdersMethod isPrepared arity />
  </#list>
</#macro>

<#macro WithInputsMethod isPrepared arity>
/**
 * Creates a (partial) DAG that will have the specified inputs.  The partial DAG will become a completed DAG once the
 * withOutputs(...) method is then called on it to specify the outputs.
 *
 * Note that this method will accept all types of {@link Producer}s, not just {@link Placeholder} objects, allowing a DAG
 * to be created from a "subgraph" that is not necessarily rooted at its original {@link Placeholder}s.  It does this by
 * effectively replacing any non-{@link Placeholder} arguments with <strong>new</strong> {@link Placeholder} objects in the
 * created DAG.
 *
 * For example, let's say we define the following graph:
 * PlaceholderA -> Transformer1
 * PlaceholderB -> Transformer1
 * GeneratorC -> Transformer1
 * Transformer1 -> Transformer2
 * PlaceholderB -> Transformer2
 * GeneratorC -> Transformer2
 *
 * This is a graph where Transformer1 receives inputs from PlaceholderA, PlaceholderB, and GeneratorC, and the
 * downstream Transformer2 receives the output of Transformer1 and the inputs from PlaceholderB and GeneratorC.
 *
 * If we create a DAG with DAG.withInputs(PlaceholderA, PlaceholderB).withOutput(Transformer2), this will be a DAG with
 * two inputs and one output, where the DAG's inputs correspond to PlaceholderA and PlaceholderB as normal.
 *
 * However, let's say that we want to instead create a subgraph, where we provide the values that would
 * otherwise be generated by Transformer1 and GeneratorC ourselves.  We can just call:
 * DAG.withInputs(PlaceholderB, Transformer1, GeneratorC).withOutput(Transformer2)
 * In this DAG, Transformer1 and GeneratorC will never run and are instead replaced by new {@link Placeholder}s, such that
 * the values provided to their child nodes are those provided to the DAG as input values.
 *
 * The ancestry of your output nodes must descend (only) from the nodes provided to withInputs(...) and from
 * Generators.  If any output node has a path to an ancestor {@link Placeholder} node that excludes the nodes passed to
 * withInputs(...), an exception will be thrown.
 *
 * withInputs(...) is intended to help you create DAGs from sub-graphs of a larger DAG.  If you just want to create an
 * "normal" DAG instance from an entire graph, use withPlaceholders(...) instead.  Using withInputs(...) works just as
 * well, but withPlaceholders(...) makes your intent to capture the entire graph obvious and is thus easier to read.
<#list 1..arity as index>
 * @param input${c.InputSuffix(index)} a producer that will root the subgraph, and whose values will be provided as inputs to the DAG.
</#list>
 * @return a partial DAG configured to use the provided producers as roots
 */
public static <<@c.InputGenericArguments arity />> PartialDAG.<#if isPrepared>Prepared.</#if><@WithPlaceholders arity /> withInput<@c.s arity />(<@c.InputProducerList arity />) {
  return new PartialDAG.<#if isPrepared>Prepared.</#if><@WithPlaceholders arity />(<#list 1..arity as index>input${c.InputSuffix(index)} instanceof Placeholder ? (Placeholder) input${c.InputSuffix(index)} : new Placeholder<>()<#sep>, </#list>, <@c.InputSuffixedList "input" arity />);
}
</#macro>

<#macro WithInputsMethods isPrepared>
  <#list 1..c.maxArity as arity>
    <@WithInputsMethod isPrepared arity />
  </#list>
</#macro>

/**
 * Static methods for creating preparable DAGs.  Use {@link DAG.Prepared} to create prepared DAGs (note that your graph
 * must contain no {@link com.linkedin.dagli.transformer.PreparableTransformer}s to be created as a prepared DAG).
 */
public final class DAG {
  private DAG() { }

  <@WithPlaceholdersMethods false />

  <@WithInputsMethods false />

  /**
   * The {@link Prepared} class is analogous to the enclosing {@link DAG} class, but is used to create prepared rather
   * than preparable DAGs.  Prepared DAGs must contain no {@link com.linkedin.dagli.transformer.PreparableTransformer}s;
   * attempting to create a prepared DAG containing a preparable transformer will result in an exception.
   */
  public static class Prepared {
    private Prepared() { }

    <@WithPlaceholdersMethods true />

    <@WithInputsMethods true />
  }

}
