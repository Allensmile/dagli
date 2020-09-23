<#import "../../common.ftl" as c />
<#import "common.ftl" as f />
<@c.AutoGeneratedWarning />
package com.linkedin.dagli.util.function;

import java.util.Objects;

<#-- the class name of the function interface we're going to define -->
<#macro NakedClassName>BooleanNegatedFunction${arity}</#macro>
<#macro ClassName><@NakedClassName /><@f.GenericArgs f.BooleanFunctionIndex arity /></#macro>
<#macro NakedInterfaceName>BooleanFunction${arity}</#macro>
<#macro InterfaceName><@NakedInterfaceName /><@f.GenericArgs f.BooleanFunctionIndex arity /></#macro>
<#macro SerializableInterfaceName><@NakedInterfaceName />.Serializable<@f.GenericArgs f.BooleanFunctionIndex arity /></#macro>

/**
 * Implements the negation of the result of a boolean function.
 *
 * This class is {@link java.io.Serializable} to allow its subclass (also named Serializable) to be serialized, but
 * should not be regarded as "safely serializable" since the function it wraps is not itself guaranteed to be
 * serializable.
 */
class <@ClassName /> implements <@InterfaceName />, java.io.Serializable {
  private static final int CLASS_HASH = <@NakedClassName />.class.hashCode();

  private final <@InterfaceName /> _function;

  // no-arg constructor for Kryo
  private <@NakedClassName />() {
    _function = null;
  }

  /**
   * Creates a new instance that will negate the result provided by the given, wrapped function.
   *
   * @param function the function to be wrapped
   */
  private <@NakedClassName />(<@InterfaceName /> function) {
    _function = Objects.requireNonNull(function);
  }

  /**
   * Returns a function that will have a result that is a negation of the function provided.  If the passed function
   * is itself of this type, its underlying (wrapped) function will be returned; otherwise, a new instance of this class
   * will be created to wrap the passed function.
   *
   * @param function the function whose result will be negated
   * @return a function (which may or may not be new) that negates the return value of the provided function
   */
  static <@f.GenericArgs f.BooleanFunctionIndex arity /> <@InterfaceName /> negate(<@InterfaceName /> function) {
    if (function instanceof <@NakedClassName />) {
      return ((<@ClassName />) function)._function; // negation of a negation is the original function
    }
    return new <@NakedClassName /><#if (arity > 0)><></#if>(function);
  }

  @Override
  public boolean apply(<@c.InputValueList arity />) {
    return !_function.apply(<@c.InputSuffixedList "value" arity />);
  }

  @Override
  public int hashCode() {
    return CLASS_HASH + _function.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof <@NakedClassName />) {
      return this._function.equals(((<@NakedClassName />) obj)._function);
    }
    return false;
  }

  static class Serializable<@f.GenericArgs f.BooleanFunctionIndex arity /> extends <@ClassName />
      implements <@SerializableInterfaceName /> {
    private static final long serialVersionUID = 1;

    // no-arg constructor for Kryo
    private Serializable() {  }

    /**
     * Creates a new instance that will negate the result provided by the given, wrapped function.
     *
     * @param function the function to be wrapped
     */
    private Serializable(<@SerializableInterfaceName /> function) {
      super(function);
    }

    /**
     * Returns a function that will have a result that is a negation of the function provided.  If the passed function
     * is itself of this type, its underlying (wrapped) function will be returned; otherwise, a new instance of this class
     * will be created to wrap the passed function.
     *
     * @param function the function whose result will be negated
     * @return a function (which may or may not be new) that negates the return value of the provided function
     */
    static <@f.GenericArgs f.BooleanFunctionIndex arity /> <@SerializableInterfaceName /> negate(<@SerializableInterfaceName /> function) {
      if (function instanceof <@NakedClassName />.Serializable) {
        // negation of a negation is the original function
        return (<@SerializableInterfaceName />)
          ((<@ClassName />) function)._function;
      }
      return new <@NakedClassName />.Serializable<#if (arity > 0)><></#if>(function);
    }
  }
}

