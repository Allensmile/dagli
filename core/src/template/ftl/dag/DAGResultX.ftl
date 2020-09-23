<#import "../common.ftl" as c />
<@c.AutoGeneratedWarning />
package com.linkedin.dagli.dag;

import com.linkedin.dagli.objectio.ObjectReader;
<#if (arity > 1)>
import com.linkedin.dagli.tuple.${c.tuples[arity]};
</#if>

<#macro GetResultXJavaDoc outputIndex returnType extraBlurb="">
  /**
   * Gets the results produced by the ${c.positionNames[outputIndex]} output node of the DAG as an {@link ${returnType}}.  This
   * {@link ${returnType}} is parallel to the examples inputted to the DAG: that is, the ith value of the
   * {@link ${returnType}} corresponds to the ith example.  The size of the {@link ${returnType}} will (of course) also
   * equal the number of examples.${extraBlurb}
   *
   * @return the values produced by the ${c.positionNames[outputIndex]} output node of the DAG for the inputted examples
   */
</#macro>

<#macro GetResultJavaDoc arity returnType extraBlurb="">
  /**
   * Gets the results of the DAG as a {@link ${returnType}}<#if (arity > 1)> of {@link ${c.tuples[arity]}}s</#if>.  This
   * {@link ${returnType}} is parallel to the examples inputted to the DAG: that is, the ith value of the
   * {@link ${returnType}} corresponds to the ith example.  The size of the {@link ${returnType}} will (of course) also
   * equal the number of examples.${extraBlurb}
   *
   * @return the values produced by the output node<@c.s arity /> of the DAG for the inputted examples
   */
</#macro>

<#assign ListJavadocWarning = "  An exception will be thrown if there are more than Integer.MAX_VALUE results." />

/**
 * Interface for obtaining the results of executing a DAG.  As a DAG may have multiple <i>output nodes</i>, whose result
 * values are the "outputs" of the DAG.  These may be retrieved together (as a tuple) directly from this interface
 * (which extends {@link ObjectReader}), or individually via the getResultX() methods.
 *
 * The ordering of the outputs is determined when the DAG is created, matching the order of the outputs passed in the
 * DAG.withPlaceholders(...).withOutputs(...) call.
 *
<#list 1..arity as arg> * @param <${c.ResultGenericArgument(arg)}> the type of result #${arg} of the DAG
</#list>
 */
public interface <@c.DAGResultInterface arity /> extends ObjectReader<<@c.ResultTuple arity />> {
  <#if (arity > 1)>
  <#list 1..arity as resultIndex>
  <@GetResultXJavaDoc resultIndex "ObjectReader" />
  public abstract ObjectReader<${c.ResultGenericArgument(resultIndex)}> getResult${resultIndex}();
  </#list>
  </#if>
}
