// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the transformer/ConstantResultTransformationX.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.transformer;

import com.linkedin.dagli.annotation.equality.ValueEquality;
import com.linkedin.dagli.preparer.PreparerContext;
import com.linkedin.dagli.preparer.TrivialPreparer7;
import com.linkedin.dagli.preparer.Preparer7;
import com.linkedin.dagli.producer.Producer;
import java.util.Objects;


/**
 * A trivial implementation of a preparable transformer that ignores its inputs and provides a constant result.
 *
 * @param <A> the type of the first input
 * @param <B> the type of the second input
 * @param <C> the type of the third input
 * @param <D> the type of the fourth input
 * @param <E> the type of the fifth input
 * @param <F> the type of the sixth input
 * @param <G> the type of the seventh input
 *
 * @param <R> the type of the constant result that will be returned by the prepared transformer.
 */
@ValueEquality
public final class ConstantResultTransformation7<A, B, C, D, E, F, G, R>
    extends
    AbstractPreparableTransformer7<A, B, C, D, E, F, G, R, ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R>, ConstantResultTransformation7<A, B, C, D, E, F, G, R>>
    implements ConstantResultTransformation<R, ConstantResultTransformation7<A, B, C, D, E, F, G, R>> {
  private static final long serialVersionUID = 1;

  // the object instance that will be the constant result of this transformer
  private R _resultForNewData = null;
  private R _resultForPreparationData = null;

  /**
   * Creates a new constant result preparable transformer that will ignore its inputs and will always has a null result.
   */
  public ConstantResultTransformation7() {
  }

  @Override
  protected boolean hasAlwaysConstantResult() {
    return true;
  }

  @Override
  protected boolean hasIdempotentPreparer() {
    return true;
  }

  /**
   * Returns a copy of this transformer that will always have the specified constant result object for both new and
   * preparation data.  Note that this exact object will be returned every time the transformer is applied (and not a
   * clone).
   *
   * @param result the result that will always be returned by this transformer
   * @return a copy of this transformer that will always have the specified constant result
   */
  public ConstantResultTransformation7<A, B, C, D, E, F, G, R> withResult(R result) {
    return clone(c -> {
      c._resultForNewData = result;
      c._resultForPreparationData = result;
    });
  }

  /**
   * Returns a copy of this transformer that will always have the specified constant result object for preparation data.
   * Note that this exact object will be returned every time the transformer is applied (and not a clone).
   *
   * @param result the result that will always be returned by this transformer for preparation data
   * @return a copy of this transformer that will always have the specified constant result
   */
  public ConstantResultTransformation7<A, B, C, D, E, F, G, R> withResultForPreparationData(R result) {
    return clone(c -> {
      c._resultForPreparationData = result;
    });
  }

  /**
   * Returns a copy of this transformer that will always have the specified constant result object for new data.
   * Note that this exact object will be returned every time the transformer is applied (and not a clone).
   *
   * @param result the result that will always be returned by this transformer for new data
   * @return a copy of this transformer that will always have the specified constant result
   */
  public ConstantResultTransformation7<A, B, C, D, E, F, G, R> withResultForNewData(R result) {
    return clone(c -> {
      c._resultForNewData = result;
    });
  }

  /**
   * @return the object instance that will be produced by this transformer for all new examples.
   */
  public R getResultForNewData() {
    return _resultForNewData;
  }

  /**
   * @return the object instance that will be produced by this transformer for all preparation examples.
   */
  public R getResultForPreparationData() {
    return _resultForPreparationData;
  }

  @Override
  protected Preparer7<A, B, C, D, E, F, G, R, ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R>> getPreparer(
      PreparerContext context) {
    ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R> resultForPrepData =
        new ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R>(_resultForPreparationData);
    return _resultForPreparationData == _resultForNewData ? new TrivialPreparer7<>(resultForPrepData)
        : new TrivialPreparer7<>(resultForPrepData, new ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R>(
            _resultForNewData));
  }

  @Override
  public ConstantResultTransformation7<A, B, C, D, E, F, G, R> withInput1(Producer<? extends A> inputA) {
    return super.withInput1(inputA);
  }

  @Override
  public ConstantResultTransformation7<A, B, C, D, E, F, G, R> withInput2(Producer<? extends B> inputB) {
    return super.withInput2(inputB);
  }

  @Override
  public ConstantResultTransformation7<A, B, C, D, E, F, G, R> withInput3(Producer<? extends C> inputC) {
    return super.withInput3(inputC);
  }

  @Override
  public ConstantResultTransformation7<A, B, C, D, E, F, G, R> withInput4(Producer<? extends D> inputD) {
    return super.withInput4(inputD);
  }

  @Override
  public ConstantResultTransformation7<A, B, C, D, E, F, G, R> withInput5(Producer<? extends E> inputE) {
    return super.withInput5(inputE);
  }

  @Override
  public ConstantResultTransformation7<A, B, C, D, E, F, G, R> withInput6(Producer<? extends F> inputF) {
    return super.withInput6(inputF);
  }

  @Override
  public ConstantResultTransformation7<A, B, C, D, E, F, G, R> withInput7(Producer<? extends G> inputG) {
    return super.withInput7(inputG);
  }

  @Override
  public String getName() {
    return "ConstantResultTransformation7("
        + (Objects.equals(_resultForNewData, _resultForPreparationData) ? _resultForNewData : ("prep = "
            + _resultForPreparationData + ", new = " + _resultForNewData)) + ")";
  }

  /**
   * A trivial implementation of a prepared transformer that ignores its inputs and provides a constant result.
   *
  * @param <A> the type of the first input
  * @param <B> the type of the second input
  * @param <C> the type of the third input
  * @param <D> the type of the fourth input
  * @param <E> the type of the fifth input
  * @param <F> the type of the sixth input
  * @param <G> the type of the seventh input
   * @param <R> the type of the constant result that will be returned by the transformer.
   */
  @ValueEquality
  public static final class Prepared<A, B, C, D, E, F, G, R>
      extends
      AbstractPreparedTransformer7<A, B, C, D, E, F, G, R, ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R>>
      implements
      ConstantResultTransformation.Prepared<R, ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R>> {
    private static final long serialVersionUID = 1;

    // the object instance that will be the constant result of this transformer
    private R _result = null;

    @Override
    protected boolean hasAlwaysConstantResult() {
      return true;
    }

    /**
     * Creates a new trivial prepared transformer that will ignore its inputs and always have a null result.
     */
    public Prepared() {
    }

    /**
     * Creates a new trivial prepared transformer that will ignore its inputs and always have the given result.
     *
     * @param result the result to be "produced"
     */
    public Prepared(R result) {
      _result = result;
    }

    /**
     * Returns a copy of this transformer that will always have the specified constant result object.  Note that this
     * exact result object will be returned every time the transformer is applied (and not a clone).
     *
     * @param result the result that will always be returned when applying the transformer
     * @return a copy of this transformer that will always have the specified constant result
     */
    public ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R> withResult(R result) {
      return clone(c -> c._result = result);
    }

    /**
     * Gets the object instance that will be produced by this transformer for all inputs.
     *
     * @return the object instance that will be produced by this transformer for all inputs.
     */
    public R getResult() {
      return _result;
    }

    @Override
    public R apply(A value1, B value2, C value3, D value4, E value5, F value6, G value7) {
      return _result;
    }

    @Override
    public ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R> withInput1(Producer<? extends A> inputA) {
      return super.withInput1(inputA);
    }

    @Override
    public ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R> withInput2(Producer<? extends B> inputB) {
      return super.withInput2(inputB);
    }

    @Override
    public ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R> withInput3(Producer<? extends C> inputC) {
      return super.withInput3(inputC);
    }

    @Override
    public ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R> withInput4(Producer<? extends D> inputD) {
      return super.withInput4(inputD);
    }

    @Override
    public ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R> withInput5(Producer<? extends E> inputE) {
      return super.withInput5(inputE);
    }

    @Override
    public ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R> withInput6(Producer<? extends F> inputF) {
      return super.withInput6(inputF);
    }

    @Override
    public ConstantResultTransformation7.Prepared<A, B, C, D, E, F, G, R> withInput7(Producer<? extends G> inputG) {
      return super.withInput7(inputG);
    }

    @Override
    public String getName() {
      return "ConstantResultTransformation7.Prepared(" + _result + ")";
    }
  }
}
