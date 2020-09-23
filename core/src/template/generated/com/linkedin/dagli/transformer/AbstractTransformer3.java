// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the transformer/AbstractTransformerX.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.transformer;

import com.linkedin.dagli.annotation.equality.IgnoredByValueEquality;
import com.linkedin.dagli.transformer.internal.Transformer3InternalAPI;
import com.linkedin.dagli.producer.Producer;
import com.linkedin.dagli.producer.MissingInput;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@IgnoredByValueEquality
abstract class AbstractTransformer3<A, B, C, R, IF extends Transformer3InternalAPI<A, B, C, R, S>, S extends AbstractTransformer3<A, B, C, R, IF, S>>
    extends AbstractTransformer<R, IF, S> implements Transformer3<A, B, C, R> {

  private static final long serialVersionUID = 1;

  @Override
  protected List<Producer<?>> getInputList() {
    return Arrays.asList(getInput1(), getInput2(), getInput3());
  }

  protected Producer<? extends A> _input1;
  protected Producer<? extends B> _input2;
  protected Producer<? extends C> _input3;

  protected Producer<? extends A> getInput1() {
    return _input1;
  }

  protected Producer<? extends B> getInput2() {
    return _input2;
  }

  protected Producer<? extends C> getInput3() {
    return _input3;
  }

  public AbstractTransformer3() {
    this(MissingInput.get(), MissingInput.get(), MissingInput.get());
  }

  public AbstractTransformer3(Producer<? extends A> input1, Producer<? extends B> input2, Producer<? extends C> input3) {
    _input1 = Objects.requireNonNull(input1);
    _input2 = Objects.requireNonNull(input2);
    _input3 = Objects.requireNonNull(input3);
  }

  /**
   * Returns a copy of this instance that will accept the specified inputs.
   *
   * The returned copy <strong>must</strong> be a new instance, as Dagli may rely on this invariant.
   *
   * @param input1 the first input
   * @param input2 the second input
   * @param input3 the third input
   * @return a copy of this instance that will accept the specified inputs
   */
  protected S withAllInputs(Producer<? extends A> input1, Producer<? extends B> input2, Producer<? extends C> input3) {
    return clone(c -> {
      c._input1 = Objects.requireNonNull(input1);
      c._input2 = Objects.requireNonNull(input2);
      c._input3 = Objects.requireNonNull(input3);
    });
  }

  /**
   * Returns a copy of this instance that will accept the provided {@link Producer} as its third
   * input.
   *
   * @param input1 the input to use
   * @return a copy of this instance that will accept the specified input
   */
  protected S withInput1(Producer<? extends A> input1) {
    return clone(c -> c._input1 = Objects.requireNonNull(input1));
  }

  /**
   * Returns a copy of this instance that will accept the provided {@link Producer} as its third
   * input.
   *
   * @param input2 the input to use
   * @return a copy of this instance that will accept the specified input
   */
  protected S withInput2(Producer<? extends B> input2) {
    return clone(c -> c._input2 = Objects.requireNonNull(input2));
  }

  /**
   * Returns a copy of this instance that will accept the provided {@link Producer} as its third
   * input.
   *
   * @param input3 the input to use
   * @return a copy of this instance that will accept the specified input
   */
  protected S withInput3(Producer<? extends C> input3) {
    return clone(c -> c._input3 = Objects.requireNonNull(input3));
  }

  protected abstract class InternalAPI extends AbstractTransformer<R, IF, S>.InternalAPI implements
      Transformer3InternalAPI<A, B, C, R, S> {
    @Override
    public Producer<? extends A> getInput1() {
      return AbstractTransformer3.this.getInput1();
    }

    @Override
    public Producer<? extends B> getInput2() {
      return AbstractTransformer3.this.getInput2();
    }

    @Override
    public Producer<? extends C> getInput3() {
      return AbstractTransformer3.this.getInput3();
    }

    @Override
    public S withInputs(Producer<? extends A> input1, Producer<? extends B> input2, Producer<? extends C> input3) {
      return AbstractTransformer3.this.withAllInputs(input1, input2, input3);
    }

    @Override
    public List<Producer<?>> getInputList() {
      return AbstractTransformer3.this.getInputList();
    }
  }
}
