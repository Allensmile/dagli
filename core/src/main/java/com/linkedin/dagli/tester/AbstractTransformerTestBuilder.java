package com.linkedin.dagli.tester;

import com.linkedin.dagli.placeholder.Placeholder;
import com.linkedin.dagli.producer.Producer;
import com.linkedin.dagli.producer.internal.ChildProducerInternalAPI;
import com.linkedin.dagli.transformer.Transformer;
import com.linkedin.dagli.util.array.ArraysEx;
import com.linkedin.dagli.util.closeable.Closeables;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Tests a {@link Transformer}.
 *
 * @param <R> the type of result produced by the {@link Transformer}
 */
abstract class AbstractTransformerTestBuilder<R, T extends Transformer<R>, S extends AbstractTransformerTestBuilder<R, T, S>>
    extends AbstractChildTestBuilder<Object[], R, T, S> {

  boolean _autogenerateParents = true;
  boolean _skipSimpleReductionTest = false;

  /**
   * Creates a new instance that will test the provided Dagli node.
   *
   * @param testSubject the primary test subject
   */
  public AbstractTransformerTestBuilder(T testSubject) {
    super(testSubject);
  }

  /**
   * Disables the autogeneration of the parents for the transformers under test.  If this method is called, all tested
   * transformers should be configured with appropriate parents by the client.
   *
   * Normally, the tester will automatically generate {@link Placeholder} parents for each transformer under testing.
   * The number parents it will configure is determined by inputs provided via {@link #input(Object, Object...)} (or
   * other input-providing methods); if no inputs are provided, a best guess is made that may not always be correct and
   * may leave the transformer in an invalid state).
   */
  @SuppressWarnings("unchecked")
  public S keepOriginalParents() {
    _autogenerateParents = false;
    return (S) this;
  }

  /**
   * Adds an input sequence to be tested.
   *
   * @param firstValue the first value of the input value sequence that will be fed to the transformer; the number of
   *                   values must match the arity of the transformer
   * @param remainingValues the subsequent values of the input value sequence that will be fed to the transformer; the
   *                        number of values must match the arity of the transformer
   * @return this instance
   */
  public S input(Object firstValue, Object... remainingValues) {
    return inputArray(ArraysEx.concat(new Object[] { firstValue }, remainingValues));
  }

  /**
   * Adds an input sequence to be tested.
   *
   * @param inputs the input value sequence that will be fed to the transformer; the number of
   *               values must match the arity of the transformer
   * @return this instance
   */
  public S inputArray(Object... inputs) {
    checkInputArity(inputs.length);
    return addInput(inputs);
  }

  private void checkInputArity(int arity) {
    if (_inputArity == -1) {
      _inputArity = arity;
    }
    assertEquals(arity, _inputArity,
        "The number of values in each supplied input sequence must be consistent");
  }

  /**
   * Adds all input sequences from a collection of input sequences to be tested.  Equivalent to call
   * {@link #inputArray(Object...)} on each element in the collection.
   *
   * @param inputs a collection of input sequences to be added
   * @return this instance
   */
  @SuppressWarnings("unchecked")
  public S allInputs(Iterable<Object[]> inputs) {
    inputs.forEach(this::inputArray);
    return (S) this;
  }

  /**
   * Adds all input sequences from parallel lists of values (each supplying one value in the input sequence).
   *
   * The first input sequence added will be:
   * [first element of parallel input list #1, first element of parallel input list #2,
   *  first element of parallel input list #3...]
   *
   * The second input sequence added will be:
   * [second element of parallel input list #1, second element of parallel input list #2,
   *  second element of parallel input list #3...]
   *
   * Etc.
   *
   * @param parallelInputLists parallel lists of values, each parallel list providing values for one of the
   *                           transformer's inputs
   * @return this instance
   */
  @SuppressWarnings("unchecked")
  public S allParallelInputs(Iterable<?>... parallelInputLists) {
    checkInputArity(parallelInputLists.length);

    Iterator<Object>[] iterators = Arrays.stream(parallelInputLists).map(Iterable::iterator).toArray(Iterator[]::new);
    try {
      while (iterators[0].hasNext()) {
        inputArray(Arrays.stream(iterators).map(Iterator::next).toArray());
      }
    } finally {
      // close the iterators (if they're closable)
      Arrays.stream(iterators).forEach(Closeables::tryClose);
    }

    return (S) this;
  }

  /**
   * Normally, transformers are placed inside a DAG, all available reductions are applied, and that DAG is also tested
   * with the same inputs and outputs.  Calling this method will disable this simple reduction test, although any
   * other reduction tests added via {@link #reductionTest(java.util.function.Predicate)} will still be performed.
   *
   * @return this instance
   */
  public S skipSimpleReductionTest() {
    return skipSimpleReductionTest(true);
  }

  /**
   * If {@code skip} is true, simple reduction testing will not be performed.  This will not affect any other reduction
   * tests added via {@link #reductionTest(java.util.function.Predicate)}.
   *
   * Normally, transformers are placed inside a DAG, all available reductions are applied, and that DAG is also tested
   * with the same inputs and outputs.
   *
   * @param skip whether to skip simple reduction testing
   * @return this instance
   */
  @SuppressWarnings("unchecked")
  public S skipSimpleReductionTest(boolean skip) {
    _skipSimpleReductionTest = skip;
    return (S) this;
  }

  private static List<Producer<?>> createPlaceholders(int count) {
    return IntStream.range(0, count)
        .mapToObj(i -> new Placeholder<>("Autogenerated Placeholder #" + i))
        .collect(Collectors.toList());
  }

  private void autogenerateParents(int count) {
    List<Producer<?>> placeholders = createPlaceholders(count);

    _testSubject = ChildProducerInternalAPI.withInputsUnsafe(_testSubject, placeholders);
    _equivalents.replaceAll(producer -> ChildProducerInternalAPI.withInputsUnsafe(producer, placeholders));
    _nonEquivalents.replaceAll(producer -> ChildProducerInternalAPI.withInputsUnsafe(producer, placeholders));
  }

  @Override
  public void test() {
    if (_autogenerateParents) {
      if (!_inputs.isEmpty()) {
        autogenerateParents(_inputs.get(0).length);
      } else {
        autogenerateParents(Math.max(1, _testSubject.internalAPI().getInputList().size()));
      }
    }
    super.test();
  }
}
