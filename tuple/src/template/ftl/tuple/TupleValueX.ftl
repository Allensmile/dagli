<#import "common.ftl" as t />
<@t.AutoGeneratedWarning />
package com.linkedin.dagli.tuple;

/**
 * An interface for retrieving value ${index} from a tuple.
 */
public interface <@t.TupleValue index /> {
  /**
   * Gets element ${index} from the tuple
   *
   * @return the element value
   */
  ${t.typeParameters[index]} get${index}();
}
