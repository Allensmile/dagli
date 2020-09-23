// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the function/FunctionResultX.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.function;

import com.linkedin.dagli.annotation.equality.IgnoredByValueEquality;
import com.linkedin.dagli.annotation.equality.ValueEquality;
import com.linkedin.dagli.producer.Producer;
import com.linkedin.dagli.transformer.AbstractPreparedTransformer1;
import com.linkedin.dagli.producer.MissingInput;

import com.linkedin.dagli.util.function.Function1;


/**
 * FunctionResult transformers apply a (serializable) function object, method reference, or lambda against their inputs.
 *
 * Versioning of the underlying function is not enforced (i.e. FunctionResult doesn't know if the implementation of a
 * method changes).
 *
 * If you're using serializable function objects or method references (e.g. String::length, str::startsWith, etc.), you
 * can stop reading here.
 *
 * For lambdas (anonymous functions, i.e. (args) -> { body }), there are strict limitations with respect to
 * serializability because they are implemented as anonymous classes.  Make sure you understand these, as otherwise your
 * DAG may be writable, but not readable, or an innocuous change may break deserialization after initially working fine!
 *
 * Because of this, you must use withFunctionUnsafe(...) to use a lambda function.  This is by design, to warn you that
 * your DAG will consequently not be safely serializable.
 *
 * Again, <strong>warning!</strong>  Lambda functions, which are implemented with anonymous classes, are inherently
 * unsafe to serialize! Lambdas are not a problem if the DAG is not serialized, but we highly discourage them in
 * any serialized setup, <strong>especially</strong> if it's production.  Three conditions must be met for lambdas to
 * serialize and deserialize successfully:
 * (1) the class in which they were created must exist in both serializing and deserializing programs.
 * (2) the ORDER in which the lambdas are defined must not change.  The names of the generated anonymous classes are
 * dependent upon the position in which the lambda appears in the file!
 * (3) the JVM should be consistent, as different JVMs are in principle free to generate different class names.
 */
@ValueEquality
public class FunctionResult1<A, R> extends AbstractPreparedTransformer1<A, R, FunctionResult1<A, R>> {
  private static final long serialVersionUID = 1;

  private Function1.Serializable<A, R> _originalFunction = null;
  @IgnoredByValueEquality
  private Function1.Serializable<A, R> _function = null;
  private boolean _nullOnNullInput = false;

  /**
   * Creates a new instance with no function and missing input.  These must be set via the withFunction(...) and
   * withInput(...) methods, respectively.
   */
  public FunctionResult1() {
    super(MissingInput.get());
  }

  /**
   * Creates a new instance that processes inputs with the specified function.
   *
   * @param func a serializable function object or a method reference.  FunctionResult will attempt to detect lambda
   *        functions and throw an exception in such cases, but as this is in principle JDK dependent these checks
   *        cannot absolutely be guaranteed to catch such abuses.  If you MUST use a lambda, call
   *        withFunctionUnsafe(...).
   */
  public FunctionResult1(Function1.Serializable<A, R> func) {
    this();
    setFunction(func.safelySerializable());
  }

  /**
   * Sets the function used by this instance.  As transformers are semantically immutable, this method must only be
   * called on <b>new</b> instances, before they are returned to the caller!
   *
   * @param func the function to be used by this instance
   */
  private void setFunction(Function1.Serializable<A, R> func) {
    _originalFunction = func;
    _function = _nullOnNullInput ? func.returnNullOnNullArgument() : func;
  }

  /**
   * Specifies that this transformer will produce a null value if any input value is null.  In such cases, the wrapped
   * function will not be called, making this useful if your inputs may contain nulls that would otherwise cause your
   * function to throw a {@link NullPointerException} or similar error.
   *
   * @return a copy of this transformer that will produce a null value if any of the input values is null.
   */
  public FunctionResult1<A, R> withNullResultOnNullInput() {
    return clone(c -> {
      c._nullOnNullInput = true;
      c.setFunction(_originalFunction);
    });
  }

  public FunctionResult1<A, R> withInput(Producer<? extends A> input1) {
    return super.withAllInputs(input1);
  }

  /**
   * Returns a copy of this instance that will process inputs with the specified function.
   *
   * @param func a serializable function object or a method reference.  FunctionResult will attempt to detect lambda
   *        functions and throw an exception in such cases, but as this is in principle JDK dependent these checks
   *        cannot absolutely be guaranteed to catch such abuses.  If you MUST use a lambda, call
   *        withFunctionUnsafe(...).
   */
  public FunctionResult1<A, R> withFunction(Function1.Serializable<A, R> func) {
    return clone(c -> c.setFunction(func.safelySerializable()));
  }

  /**
   * Returns a copy of this instance that will process inputs with the specified function.  This method is UNSAFE
   * because it permits arbitrary lambda functions, which are very difficult to safely serialize.  We strongly caution
   * against using this method if you need FunctionResult (and thus your DAG as a whole) to be reliably deserializable.
   *
   * @param func an arbitrary lambda function
   */
  public FunctionResult1<A, R> withFunctionUnsafe(Function1.Serializable<A, R> func) {
    return clone(c -> {
      try {
        c.setFunction(func.safelySerializable());
      } catch (RuntimeException e) {
        c.setFunction(func);
      }
    });
  }

  @Override
  public R apply(A input1) {
    return _function.apply(input1);
  }

  /**
   * Returns a new {@link FunctionResult1} that composes this instance's function with another "mapper" function such
   * that the result is {@code mapper.apply(thisFunction.apply(...))}.  The other properties of the returned
   * transformer, including the inputs, remain the same as this one.
   *
   * Note that, while very close, this is not <i>exactly</i> equivalent to
   * {@code new FunctionResult1(someFunction).withInput(thisTransformer)} because the intermediate results never transit
   * through the DAG, which is slightly more efficient and means that, e.g. these intermediates can safely share or
   * reuse (thread-local) state.
   *
   * @param mapper a function that will map the result of this instance's function; this function must be safely
   *               serializable (see {@link Function1.Serializable#safelySerializable()}).
   * @param <Q> the type of the final result of the composed function
   * @return a new {@link FunctionResult1} that composes this instance's function with another "mapper" function
   */
  @SuppressWarnings("unchecked")
  public <Q> FunctionResult1<A, Q> andThen(Function1.Serializable<? super R, ? extends Q> mapper) {
    return (FunctionResult1<A, Q>) clone(c -> ((FunctionResult1<A, Q>) c).setFunction(_originalFunction.andThen(mapper
        .safelySerializable())));
  }
}
