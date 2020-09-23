<#import "../../common.ftl" as c />
<#import "../transformer.ftl" as t />
<@c.AutoGeneratedWarning />
package com.linkedin.dagli.transformer.internal;

import com.linkedin.dagli.dag.DAG;
import com.linkedin.dagli.placeholder.Placeholder;
import com.linkedin.dagli.producer.Producer;

<#if (arity < c.maxArity)>
  import com.linkedin.dagli.transformer.PreparedTransformer${arity+1};
</#if>

<#if (arity > 1)>
  import com.linkedin.dagli.transformer.PreparedTransformer1;
  import com.linkedin.dagli.tuple.${c.tuples[arity]};
    <#list 0..arity-1 as index>
      import com.linkedin.dagli.transformer.Value${index}FromTuple;
    </#list>
</#if>

import java.util.List;
import com.linkedin.dagli.transformer.PreparedTransformer${arity};

<#assign subclass>S extends <@c.PreparedTransformer arity /></#assign>
public interface <@c.PreparedTransformerInternalAPI arity subclass />
    extends <@c.TransformerInternalAPI arity "S" />, PreparedTransformerInternalAPI<R, S> {

  R apply(Object executionCache, <@c.InputSuffixedParameters "value" arity />);

  @Override
  @SuppressWarnings("unchecked")
  default R applyUnsafe(Object executionCache, Object[] values) {
    assert values.length >= ${arity};
    return apply(executionCache, <#list 1..arity as index>(${c.InputGenericArgument(index)}) values[${index-1}]<#sep>, </#list>);
  }

  @Override
  @SuppressWarnings("unchecked")
  default R applyUnsafe(Object executionCache, List<?> values) {
    assert values.size() >= ${arity};
    return apply(executionCache, <#list 1..arity as index>(${c.InputGenericArgument(index)}) values.get(${index-1})<#sep>, </#list>);
  }

  @Override
  @SuppressWarnings("unchecked")
  default R applyUnsafe(Object executionCache, Object[][] values, int exampleIndex) {
    assert values.length >= ${arity};
    return apply(executionCache, <#list 1..arity as index>(${c.InputGenericArgument(index)}) values[${index-1}][exampleIndex]<#sep>, </#list>);
  }

  <@t.WithArityMethods arity true />
}
