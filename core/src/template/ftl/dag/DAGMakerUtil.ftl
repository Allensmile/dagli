<#import "../common.ftl" as c />
<@c.AutoGeneratedWarning />
package com.linkedin.dagli.dag;

import com.linkedin.dagli.producer.MissingInput;

/**
 * Internal Dagli utility class for creating the appropriate DAGXxY instance (once with the appropriate input arity X
 * and appropriate result arity Y) for a given DAGStructure.
 */
final class DAGMakerUtil {
  private DAGMakerUtil() { }

  <#list [false, true] as prepared>
  /**
   * Creates a DAGXxY<#if prepared>.Prepared</#if> instance with the appropriate input arity X and appropriate for the provided graph.
   * @param dag the graph from which to create the DAGXxY<#if prepared>.Prepared</#if> instance
   * @param <R> the result tuple type of the graph (e.g. String, Tuple2<String, Integer>, Tuple3<...>, ...)
   * @return a DAGXxY<#if prepared>.Prepared</#if> instance with the appropriate input arity X and appropriate for the provided graph
   */
  static <R><#if prepared>PreparedDAGTransformer<R, ?><#else>PreparableDAGTransformer<R, ?, ?></#if> make<#if prepared>Prepared<#else>Preparable</#if>DAGTransformer(DAGStructure<R> dag) {
    switch (dag.getInputArity()) {
      <#list 1..c.maxArity as placeholderArity>
      case ${placeholderArity}:
        switch (dag.getOutputArity()) {
          <#list 1..c.maxArity as outputArity>
          case ${outputArity}:
            return new <#if prepared><@c.PreparedDAGClassName placeholderArity outputArity /><#else><@c.DAGClassName placeholderArity outputArity /></#if>(dag, <@c.MissingInputs placeholderArity />);
          </#list>
          default:
            throw new UnsupportedOperationException("Unsupported output arity");
        }
      </#list>
      default:
        throw new UnsupportedOperationException("Unsupported placeholder arity");
    }
  }
  </#list>
}