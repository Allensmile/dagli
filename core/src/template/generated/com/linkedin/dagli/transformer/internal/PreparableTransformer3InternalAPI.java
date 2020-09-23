// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the transformer/internal/PreparableTransformerXInternalAPI.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.transformer.internal;

import com.linkedin.dagli.objectio.ConcatenatedReader;
import com.linkedin.dagli.transformer.PreparableTransformer3;
import com.linkedin.dagli.transformer.PreparedTransformer3;
import com.linkedin.dagli.preparer.Preparer3;
import com.linkedin.dagli.preparer.PreparerContext;
import com.linkedin.dagli.preparer.PreparerResultMixed;
import com.linkedin.dagli.dag.DAGExecutor;
import java.util.Collection;
import com.linkedin.dagli.objectio.ObjectIterator;
import com.linkedin.dagli.objectio.ObjectReader;

import com.linkedin.dagli.dag.DAG;
import com.linkedin.dagli.placeholder.Placeholder;
import com.linkedin.dagli.producer.Producer;

import com.linkedin.dagli.transformer.PreparableTransformer1;
import com.linkedin.dagli.transformer.PreparedTransformer1;

import com.linkedin.dagli.transformer.PreparableTransformer4;
import com.linkedin.dagli.transformer.PreparedTransformer4;

import com.linkedin.dagli.tuple.Tuple3;
import com.linkedin.dagli.transformer.Value0FromTuple;
import com.linkedin.dagli.transformer.Value1FromTuple;
import com.linkedin.dagli.transformer.Value2FromTuple;


public interface PreparableTransformer3InternalAPI<A, B, C, R, N extends PreparedTransformer3<A, B, C, R>, S extends PreparableTransformer3<A, B, C, R, N>>
    extends Transformer3InternalAPI<A, B, C, R, S>, PreparableTransformerInternalAPI<R, N, S> {

  @Override
  Preparer3<A, B, C, R, N> getPreparer(PreparerContext context);

  default PreparerResultMixed<? extends PreparedTransformer3<? super A, ? super B, ? super C, ? extends R>, N> prepare(
      PreparerContext context, Iterable<? extends A> values1, Iterable<? extends B> values2,
      Iterable<? extends C> values3) {
    Preparer3<A, B, C, R, N> preparer = getPreparer(context);

    try (ObjectIterator<? extends A> iter1 = ObjectIterator.wrap(values1.iterator());
        ObjectIterator<? extends B> iter2 = ObjectIterator.wrap(values2.iterator());
        ObjectIterator<? extends C> iter3 = ObjectIterator.wrap(values3.iterator())) {
      while (iter1.hasNext()) {
        preparer.process(iter1.next(), iter2.next(), iter3.next());
      }

      assert !iter2.hasNext();
      assert !iter3.hasNext();
    }

    return preparer.finishUnsafe(new ConcatenatedReader<>(Object[]::new, ObjectReader.wrap(values1), ObjectReader
        .wrap(values2), ObjectReader.wrap(values3)));
  }

  default PreparerResultMixed<? extends PreparedTransformer3<? super A, ? super B, ? super C, ? extends R>, N> prepare(
      DAGExecutor executor, Collection<? extends A> values1, Collection<? extends B> values2,
      Collection<? extends C> values3) {
    return prepare(PreparerContext.builder(values1.size()).setExecutor(executor).build(), values1, values2, values3);
  }

  @Override
  default <D> PreparableTransformer4<A, B, C, D, R, ? extends PreparedTransformer4<A, B, C, D, R>> withArity4(
      Producer<? extends D> newInput4) {
    Placeholder<A> nestedPlaceholder1 = new Placeholder<>("Original Input 1");
    Placeholder<B> nestedPlaceholder2 = new Placeholder<>("Original Input 2");
    Placeholder<C> nestedPlaceholder3 = new Placeholder<>("Original Input 3");
    return DAG
        .withPlaceholders(nestedPlaceholder1, nestedPlaceholder2, nestedPlaceholder3, new Placeholder<D>("Ignored"))
        .withOutput(withInputs(nestedPlaceholder1, nestedPlaceholder2, nestedPlaceholder3))
        .withInputs(getInput1(), getInput2(), getInput3(), newInput4);
  }

  @Override
  default <N> PreparableTransformer4<N, A, B, C, R, ? extends PreparedTransformer4<N, A, B, C, R>> withPrependedArity4(
      Producer<? extends N> newInput1) {
    Placeholder<A> nestedPlaceholder1 = new Placeholder<>("Original Input 1");
    Placeholder<B> nestedPlaceholder2 = new Placeholder<>("Original Input 2");
    Placeholder<C> nestedPlaceholder3 = new Placeholder<>("Original Input 3");
    return DAG
        .withPlaceholders(new Placeholder<N>("Ignored"), nestedPlaceholder1, nestedPlaceholder2, nestedPlaceholder3)
        .withOutput(this.withInputs(nestedPlaceholder1, nestedPlaceholder2, nestedPlaceholder3))
        .withInputs(newInput1, getInput1(), getInput2(), getInput3());
  }

  @Override
  default PreparableTransformer1<Tuple3<? extends A, ? extends B, ? extends C>, R, ? extends PreparedTransformer1<Tuple3<? extends A, ? extends B, ? extends C>, R>> withArity1(
      Producer<? extends Tuple3<? extends A, ? extends B, ? extends C>> inputTuple) {
    Placeholder<Tuple3<? extends A, ? extends B, ? extends C>> placeholderTuple =
        new Placeholder<>("Original Inputs Tuple");

    return DAG
        .withPlaceholder(placeholderTuple)
        .withOutput(
            withInputs(new Value0FromTuple<>(placeholderTuple), new Value1FromTuple<>(placeholderTuple),
                new Value2FromTuple<>(placeholderTuple))).withInput(inputTuple);
  }
}
