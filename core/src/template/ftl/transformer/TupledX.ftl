<#import "../common.ftl" as c />
<@c.AutoGeneratedWarning />
package com.linkedin.dagli.transformer;

import com.linkedin.dagli.annotation.equality.ValueEquality;
import com.linkedin.dagli.producer.Producer;
import com.linkedin.dagli.tuple.${c.tuples[arity]};

/**
 * A transformer that produces a tuple of the values it receives as inputs.
 */
@ValueEquality
public class <@c.Tupled arity /> extends AbstractPreparedTransformer${arity}<<@c.InputGenericArguments arity />, ${c.tuples[arity]}<<@c.InputGenericArguments arity />>, <@c.Tupled arity />> {
  private static final long serialVersionUID = 1;

  /**
   * Creates a new instance with the specified inputs.
   */
  public Tupled${arity}(<@c.InputProducerList arity />) {
    super(<@c.InputSuffixedList "input" arity />);
  }

  @Override
  public ${c.tuples[arity]}<<@c.InputGenericArguments arity />> apply(<@c.InputSuffixedParameters "value" arity />) {
    return ${c.tuples[arity]}.of(<@c.InputSuffixedList "value" arity />);
  }
}
