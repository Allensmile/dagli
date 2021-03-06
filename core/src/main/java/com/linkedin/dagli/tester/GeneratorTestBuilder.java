package com.linkedin.dagli.tester;

import com.linkedin.dagli.generator.Generator;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;


/**
 * Tests a {@link Generator}.
 *
 * @param <R> the type of result generated by the generator
 */
public final class GeneratorTestBuilder<R, T extends Generator<R>>
    extends AbstractTestBuilder<R, T, GeneratorTestBuilder<R, T>> {
  /**
   * Creates a new instance that will test the provided Dagli node.
   *
   * @param testSubject the primary test subject
   */
  public GeneratorTestBuilder(T testSubject) {
    super(testSubject);
  }

  @Override
  public void test() {
    super.test();
    checkAll(subject -> checkGeneratedValues(subject, _outputsTesters));
  }

  void checkGeneratedValues(Generator<R> generator, List<Predicate<? super R>> outputTests) {
    HashSet<R> resultSet = new HashSet<>();

    for (int i = 0; i < outputTests.size(); i++) {
      R result = generator.generate(i);
      if (!outputTests.get(i).test(result)) {
        throw new AssertionError("Generated value from " + generator + " for example index " + i + " was " + result
            + ", which does not satisfy the test " + outputTests.get(i));
      }

      if (_distinctOutputs && !resultSet.add(result)) {
        throw new AssertionError("Generated value from " + generator + " for example index " + i + " is " + result
            + ", which is equals() to a another generated result.  This is an error because this test was configured "
            + "with distinctOutputs()");
      }
    }
  }
}
