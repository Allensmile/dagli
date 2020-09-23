// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the transformer/AbstractTransformerX.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.transformer;

import com.linkedin.dagli.annotation.equality.IgnoredByValueEquality;
import com.linkedin.dagli.transformer.internal.Transformer7InternalAPI;
import com.linkedin.dagli.producer.Producer;
import com.linkedin.dagli.producer.MissingInput;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@IgnoredByValueEquality
abstract class AbstractTransformer7<A, B, C, D, E, F, G, R, IF extends Transformer7InternalAPI<A, B, C, D, E, F, G, R, S>, S extends AbstractTransformer7<A, B, C, D, E, F, G, R, IF, S>>
    extends AbstractTransformer<R, IF, S> implements Transformer7<A, B, C, D, E, F, G, R> {

  private static final long serialVersionUID = 1;

  @Override
  protected List<Producer<?>> getInputList() {
    return Arrays.asList(getInput1(), getInput2(), getInput3(), getInput4(), getInput5(), getInput6(), getInput7());
  }

  protected Producer<? extends A> _input1;
  protected Producer<? extends B> _input2;
  protected Producer<? extends C> _input3;
  protected Producer<? extends D> _input4;
  protected Producer<? extends E> _input5;
  protected Producer<? extends F> _input6;
  protected Producer<? extends G> _input7;

  protected Producer<? extends A> getInput1() {
    return _input1;
  }

  protected Producer<? extends B> getInput2() {
    return _input2;
  }

  protected Producer<? extends C> getInput3() {
    return _input3;
  }

  protected Producer<? extends D> getInput4() {
    return _input4;
  }

  protected Producer<? extends E> getInput5() {
    return _input5;
  }

  protected Producer<? extends F> getInput6() {
    return _input6;
  }

  protected Producer<? extends G> getInput7() {
    return _input7;
  }

  public AbstractTransformer7() {
    this(MissingInput.get(), MissingInput.get(), MissingInput.get(), MissingInput.get(), MissingInput.get(),
        MissingInput.get(), MissingInput.get());
  }

  public AbstractTransformer7(Producer<? extends A> input1, Producer<? extends B> input2, Producer<? extends C> input3,
      Producer<? extends D> input4, Producer<? extends E> input5, Producer<? extends F> input6,
      Producer<? extends G> input7) {
    _input1 = Objects.requireNonNull(input1);
    _input2 = Objects.requireNonNull(input2);
    _input3 = Objects.requireNonNull(input3);
    _input4 = Objects.requireNonNull(input4);
    _input5 = Objects.requireNonNull(input5);
    _input6 = Objects.requireNonNull(input6);
    _input7 = Objects.requireNonNull(input7);
  }

  /**
   * Returns a copy of this instance that will accept the specified inputs.
   *
   * The returned copy <strong>must</strong> be a new instance, as Dagli may rely on this invariant.
   *
   * @param input1 the first input
   * @param input2 the second input
   * @param input3 the third input
   * @param input4 the fourth input
   * @param input5 the fifth input
   * @param input6 the sixth input
   * @param input7 the seventh input
   * @return a copy of this instance that will accept the specified inputs
   */
  protected S withAllInputs(Producer<? extends A> input1, Producer<? extends B> input2, Producer<? extends C> input3,
      Producer<? extends D> input4, Producer<? extends E> input5, Producer<? extends F> input6,
      Producer<? extends G> input7) {
    return clone(c -> {
      c._input1 = Objects.requireNonNull(input1);
      c._input2 = Objects.requireNonNull(input2);
      c._input3 = Objects.requireNonNull(input3);
      c._input4 = Objects.requireNonNull(input4);
      c._input5 = Objects.requireNonNull(input5);
      c._input6 = Objects.requireNonNull(input6);
      c._input7 = Objects.requireNonNull(input7);
    });
  }

  /**
   * Returns a copy of this instance that will accept the provided {@link Producer} as its seventh
   * input.
   *
   * @param input1 the input to use
   * @return a copy of this instance that will accept the specified input
   */
  protected S withInput1(Producer<? extends A> input1) {
    return clone(c -> c._input1 = Objects.requireNonNull(input1));
  }

  /**
   * Returns a copy of this instance that will accept the provided {@link Producer} as its seventh
   * input.
   *
   * @param input2 the input to use
   * @return a copy of this instance that will accept the specified input
   */
  protected S withInput2(Producer<? extends B> input2) {
    return clone(c -> c._input2 = Objects.requireNonNull(input2));
  }

  /**
   * Returns a copy of this instance that will accept the provided {@link Producer} as its seventh
   * input.
   *
   * @param input3 the input to use
   * @return a copy of this instance that will accept the specified input
   */
  protected S withInput3(Producer<? extends C> input3) {
    return clone(c -> c._input3 = Objects.requireNonNull(input3));
  }

  /**
   * Returns a copy of this instance that will accept the provided {@link Producer} as its seventh
   * input.
   *
   * @param input4 the input to use
   * @return a copy of this instance that will accept the specified input
   */
  protected S withInput4(Producer<? extends D> input4) {
    return clone(c -> c._input4 = Objects.requireNonNull(input4));
  }

  /**
   * Returns a copy of this instance that will accept the provided {@link Producer} as its seventh
   * input.
   *
   * @param input5 the input to use
   * @return a copy of this instance that will accept the specified input
   */
  protected S withInput5(Producer<? extends E> input5) {
    return clone(c -> c._input5 = Objects.requireNonNull(input5));
  }

  /**
   * Returns a copy of this instance that will accept the provided {@link Producer} as its seventh
   * input.
   *
   * @param input6 the input to use
   * @return a copy of this instance that will accept the specified input
   */
  protected S withInput6(Producer<? extends F> input6) {
    return clone(c -> c._input6 = Objects.requireNonNull(input6));
  }

  /**
   * Returns a copy of this instance that will accept the provided {@link Producer} as its seventh
   * input.
   *
   * @param input7 the input to use
   * @return a copy of this instance that will accept the specified input
   */
  protected S withInput7(Producer<? extends G> input7) {
    return clone(c -> c._input7 = Objects.requireNonNull(input7));
  }

  protected abstract class InternalAPI extends AbstractTransformer<R, IF, S>.InternalAPI implements
      Transformer7InternalAPI<A, B, C, D, E, F, G, R, S> {
    @Override
    public Producer<? extends A> getInput1() {
      return AbstractTransformer7.this.getInput1();
    }

    @Override
    public Producer<? extends B> getInput2() {
      return AbstractTransformer7.this.getInput2();
    }

    @Override
    public Producer<? extends C> getInput3() {
      return AbstractTransformer7.this.getInput3();
    }

    @Override
    public Producer<? extends D> getInput4() {
      return AbstractTransformer7.this.getInput4();
    }

    @Override
    public Producer<? extends E> getInput5() {
      return AbstractTransformer7.this.getInput5();
    }

    @Override
    public Producer<? extends F> getInput6() {
      return AbstractTransformer7.this.getInput6();
    }

    @Override
    public Producer<? extends G> getInput7() {
      return AbstractTransformer7.this.getInput7();
    }

    @Override
    public S withInputs(Producer<? extends A> input1, Producer<? extends B> input2, Producer<? extends C> input3,
        Producer<? extends D> input4, Producer<? extends E> input5, Producer<? extends F> input6,
        Producer<? extends G> input7) {
      return AbstractTransformer7.this.withAllInputs(input1, input2, input3, input4, input5, input6, input7);
    }

    @Override
    public List<Producer<?>> getInputList() {
      return AbstractTransformer7.this.getInputList();
    }
  }
}
