<#import "../../common.ftl" as c />
<#import "common.ftl" as f />
<@c.AutoGeneratedWarning />
package com.linkedin.dagli.util.function;

import com.linkedin.dagli.util.exception.Exceptions;

<#-- true if a Java functional interface will be extended -->
<#assign hasJavaInterface = arity <= 2 && (typeIndex == f.GenericFunctionIndex || !f.JavaInterfaceIsGeneric(typeIndex, arity)) />

<#if hasJavaInterface>
<@f.JavaInterfaceImport typeIndex arity />
</#if>

<#-- Special case import -->
<#if typeIndex == f.GenericFunctionIndex && arity == 2>import java.util.function.Function;</#if>

<#-- the class name of the function interface we're going to define -->
<#macro NakedClassName>${f.Prefix(typeIndex)}Function${arity}</#macro>
<#macro ClassName><@NakedClassName /><@f.GenericArgs typeIndex arity /></#macro>

<#macro NakedMethodReferenceName>${f.Prefix(typeIndex)}MethodReference${arity}</#macro>
<#macro MethodReferenceName><@NakedMethodReferenceName /><@f.GenericArgs typeIndex arity /></#macro>

<#macro MapMethods serializable>
  <#-- add map methods (iff this is a generic FunctionX function) -->
  <#if typeIndex == f.GenericFunctionIndex>
  <#list 0..<f.typeTuples?size as composedTypeIndex>
  /**
   * Composes this function with another by mapping the result of this function.  The returned function is equivalent
   * to {@code mapper.apply(this.apply(...))}.
   *
   * This method is analogous to {@link Function#andThen(Function)}.
   *
   * @param mapper the function that will map the result of this one in the returned composed function<#if composedTypeIndex == f.GenericFunctionIndex>
   * @param <Q> the type of the result of the composed function</#if>
   * @return a composed function equivalent to {@code mapper.apply(this.apply(...))}
   */
  default<#if composedTypeIndex == f.GenericFunctionIndex> <Q></#if> ${f.Prefix(composedTypeIndex)}Function${arity}<#if serializable>.Serializable</#if><@f.GenericArgs composedTypeIndex arity "" "Q" /> andThen<#if (composedTypeIndex > 0)>To${f.Prefix(composedTypeIndex)}</#if>(
      ${f.Prefix(composedTypeIndex)}Function1<#if serializable>.Serializable</#if><? super R<#if (composedTypeIndex == 0)>, ? extends Q</#if>> mapper) {
    return new ${f.Prefix(composedTypeIndex)}ComposedFunction${arity}<>(this, mapper);
  }
  </#list>
  </#if>
</#macro>

@FunctionalInterface
<#if (arity <= f.MaxPublicArity)>public </#if>interface <@ClassName /> extends FunctionBase<#if hasJavaInterface><@f.JavaInterface typeIndex arity /></#if> {
  ${f.ReturnedPrimitiveName(typeIndex)} apply(<@c.InputValueList arity />);

  <#-- default on null argument method -->
  <#if (arity > 0)>
  /**
   * Returns a new function that wraps this "original" function.  The wrapping function will return ${f.defaultValues[typeIndex]}
   * without calling the original function if any argument is null.  If no argument is null, the original function will
   * be called as normal and its return value will be returned by the wrapping function.
   *
   * This is useful when you have a function that cannot accept a null as an argument but are satisfied to just return
   * a default value in such instances.
   *
   * @return a new function that calls this one if all arguments or non-null, or returns a default value otherwise.
   */
  default <@ClassName /> <#if typeIndex == f.VoidFunctionIndex>skip<#else>return${f.defaultValueNames[typeIndex]}</#if>OnNullArgument() {
    return new ${f.Prefix(typeIndex)}DefaultOnNullArgument${arity}<>(this);
  }
  </#if>

  <@MapMethods false />

  <#-- some methods are added as a special case -->
  <#if (typeIndex == f.GenericFunctionIndex && arity == 1)>
    @Override
    default <V> Function1<V, R> compose(Function<? super V, ? extends A> before) {
      return Function.super.compose(before)::apply;
    }

    @Override
    default <V> Function1<A, V> andThen(Function<? super R, ? extends V> after) {
      return Function.super.andThen(after)::apply;
    }

    static <T> UnaryFunction.Serializable<T> identity() {
      return (UnaryFunction.Serializable<T>) IdentityFunctionInstance.INSTANCE;
    }
  <#elseif (typeIndex == f.GenericFunctionIndex && arity == 2)>
    @Override
    default <V> Function2<A, B, V> andThen(Function<? super R, ? extends V> after) {
      return BiFunction.super.andThen(after)::apply;
    }
  <#elseif (typeIndex == f.BooleanFunctionIndex)>
    <#if (arity == 1 || arity == 2)>@Override</#if>
    default <@ClassName /> negate() {
      return BooleanNegatedFunction${arity}.negate(this);
    }
  </#if>

  <#-- Function1 and Function2 are excluded here because their apply method already matches the Java -->
  <#-- Function/BiFunction @FunctionalInterface methods -->
  <#if (arity <= 2 && hasJavaInterface && typeIndex != f.GenericFunctionIndex)>
  @Override
  default <#if f.JavaInterfaceIsGeneric(typeIndex, arity)>${f.ReturnedClassName(typeIndex)}<#else>${f.ReturnedPrimitiveName(typeIndex)}</#if> <@f.JavaInterfaceMethod typeIndex arity />(<@c.InputValueList arity />) {
    <#if typeIndex != f.VoidFunctionIndex>return </#if>apply(<@c.InputSuffixedList "value" arity />);
  }
  <#elseif (typeIndex == f.GenericFunctionIndex && arity == 0)>
  @Override
  default R get() {
    return apply();
  }
  </#if>

  static <@f.GenericArgs typeIndex arity /> <@ClassName /> unchecked(Checked<@f.GenericArgs typeIndex arity "?" /> checkedFunction) {
    return (<@c.InputValueList arity />) -> {
      try {
        <#if typeIndex != f.VoidFunctionIndex>return </#if>checkedFunction.apply(<@c.InputSuffixedList "value" arity />);
      } catch (Throwable e) {
        throw Exceptions.asRuntimeException(e);
      }
    };
  }

  @FunctionalInterface
  interface Checked<@f.GenericArgs typeIndex arity "X extends Throwable" /> extends FunctionBase {
    ${f.ReturnedPrimitiveName(typeIndex)} apply(<@c.InputValueList arity />) throws X;
  }

  interface Serializable<@f.GenericArgs typeIndex arity /> extends <@ClassName />, java.io.Serializable {
    /**
     * Creates a new, safely-serializable function from this one if this is a method reference (e.g. Object::toString),
     * or simply returns this if this is a function object.  If this is something not safely serializable (e.g. a
     * lambda), an exception will be thrown.
     *
     * "Safely-serializable" means that a function can be deserialized in a way that is not inherently brittle.
     * We recommend only serializing functions when they are safely-serializable, but note that this is not a guarantee;
     * as with Serializable objects in general it's always possible to create something (safely-)serializable that will
     * not serialize, e.g. an instance method with a captured instance (e.g. new Object()::toString) where the captured
     * instance is not itself serializable.
     *
     * Function objects that wrap functions and implement <@NakedClassName />.Serializable should override this method
     * when appropriate.  Generally such an implementation will simply create a new instance wrapping
     * wrappedFunction.safelySerializable() instead of wrappedFunction.
     *
     * Anonymous lambdas, such as "a -> a + 5", are *not* safely-serializable, even if they are technically
     * serializable, as they are extraordinarily fragile and will only deserialize correctly under these conditions:
     * (1) the class in which they were created must exist in both serializing and deserializing programs.
     * (2) the ORDER in which the lambdas are defined must not change.  The names of the generated anonymous classes are
     * dependent upon the position in which the lambda appears in the file!
     * (3) the JVM should be consistent, as different JVMs are in principle free to generate different class names.
     */
    default Serializable<@f.GenericArgs typeIndex arity /> safelySerializable() {
      try {
        return new <@NakedMethodReferenceName /><@f.GenericArgs typeIndex arity />(this);
      } catch (java.lang.RuntimeException e) {
        if (e.getCause() instanceof java.lang.NoSuchMethodException) {
          // must be a function object
          return this;
        } else {
          // anonymous lambda or something went wrong
          throw e;
        }
      }
    }

    <#-- default on null argument method -->
    <#if (arity > 0)>
    @Override
    default Serializable<@f.GenericArgs typeIndex arity /> <#if typeIndex == f.VoidFunctionIndex>skip<#else>return${f.defaultValueNames[typeIndex]}</#if>OnNullArgument() {
      return new ${f.Prefix(typeIndex)}DefaultOnNullArgument${arity}(this);
    }
    </#if>

    <#if (typeIndex == f.BooleanFunctionIndex)>
    @Override
    default Serializable<@f.GenericArgs typeIndex arity /> negate() {
      return BooleanNegatedFunction${arity}.Serializable.negate(this);
    }
    </#if>

    <@MapMethods true />
  }
}
