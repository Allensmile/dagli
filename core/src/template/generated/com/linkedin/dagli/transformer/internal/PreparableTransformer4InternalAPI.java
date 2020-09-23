// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the transformer/internal/PreparableTransformerXInternalAPI.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.transformer.internal;

import com.linkedin.dagli.objectio.ConcatenatedReader;
import com.linkedin.dagli.transformer.PreparableTransformer4;
import com.linkedin.dagli.transformer.PreparedTransformer4;
import com.linkedin.dagli.preparer.Preparer4;
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

import com.linkedin.dagli.transformer.PreparableTransformer5;
import com.linkedin.dagli.transformer.PreparedTransformer5;

import com.linkedin.dagli.tuple.Tuple4;
import com.linkedin.dagli.transformer.Value0FromTuple;
import com.linkedin.dagli.transformer.Value1FromTuple;
import com.linkedin.dagli.transformer.Value2FromTuple;
import com.linkedin.dagli.transformer.Value3FromTuple;


public interface PreparableTransformer4InternalAPI<A, B, C, D, R, N extends PreparedTransformer4<A, B, C, D, R>, S extends PreparableTransformer4<A, B, C, D, R, N>>
    extends Transformer4InternalAPI<A, B, C, D, R, S>, PreparableTransformerInternalAPI<R, N, S> {

  @Override
  Preparer4<A, B, C, D, R, N> getPreparer(PreparerContext context);

  default PreparerResultMixed<? extends PreparedTransformer4<? super A, ? super B, ? super C, ? super D, ? extends R>, N> prepare(
      PreparerContext context, Iterable<? extends A> values1, Iterable<? extends B> values2,
      Iterable<? extends C> values3, Iterable<? extends D> values4) {
    Preparer4<A, B, C, D, R, N> preparer = getPreparer(context);

    try (ObjectIterator<? extends A> iter1 = ObjectIterator.wrap(values1.iterator());
        ObjectIterator<? extends B> iter2 = ObjectIterator.wrap(values2.iterator());
        ObjectIterator<? extends C> iter3 = ObjectIterator.wrap(values3.iterator());
        ObjectIterator<? extends D> iter4 = ObjectIterator.wrap(values4.iterator())) {
      while (iter1.hasNext()) {
        preparer.process(iter1.next(), iter2.next(), iter3.next(), iter4.next());
      }

      assert !iter2.hasNext();
      assert !iter3.hasNext();
      assert !iter4.hasNext();
    }

    return preparer.finishUnsafe(new ConcatenatedReader<>(Object[]::new, ObjectReader.wrap(values1), ObjectReader
        .wrap(values2), ObjectReader.wrap(values3), ObjectReader.wrap(values4)));
  }

  default PreparerResultMixed<? extends PreparedTransformer4<? super A, ? super B, ? super C, ? super D, ? extends R>, N> prepare(
      DAGExecutor executor, Collection<? extends A> values1, Collection<? extends B> values2,
      Collection<? extends C> values3, Collection<? extends D> values4) {
    return prepare(PreparerContext.builder(values1.size()).setExecutor(executor).build(), values1, values2, values3,
        values4);
  }

  @Override
  default <E> PreparableTransformer5<A, B, C, D, E, R, ? extends PreparedTransformer5<A, B, C, D, E, R>> withArity5(
      Producer<? extends E> newInput5) {
    Placeholder<A> nestedPlaceholder1 = new Placeholder<>("Original Input 1");
    Placeholder<B> nestedPlaceholder2 = new Placeholder<>("Original Input 2");
    Placeholder<C> nestedPlaceholder3 = new Placeholder<>("Original Input 3");
    Placeholder<D> nestedPlaceholder4 = new Placeholder<>("Original Input 4");
    return DAG
        .withPlaceholders(nestedPlaceholder1, nestedPlaceholder2, nestedPlaceholder3, nestedPlaceholder4,
            new Placeholder<E>("Ignored"))
        .withOutput(withInputs(nestedPlaceholder1, nestedPlaceholder2, nestedPlaceholder3, nestedPlaceholder4))
        .withInputs(getInput1(), getInput2(), getInput3(), getInput4(), newInput5);
  }

  @Override
  default <N> PreparableTransformer5<N, A, B, C, D, R, ? extends PreparedTransformer5<N, A, B, C, D, R>> withPrependedArity5(
      Producer<? extends N> newInput1) {
    Placeholder<A> nestedPlaceholder1 = new Placeholder<>("Original Input 1");
    Placeholder<B> nestedPlaceholder2 = new Placeholder<>("Original Input 2");
    Placeholder<C> nestedPlaceholder3 = new Placeholder<>("Original Input 3");
    Placeholder<D> nestedPlaceholder4 = new Placeholder<>("Original Input 4");
    return DAG
        .withPlaceholders(new Placeholder<N>("Ignored"), nestedPlaceholder1, nestedPlaceholder2, nestedPlaceholder3,
            nestedPlaceholder4)
        .withOutput(this.withInputs(nestedPlaceholder1, nestedPlaceholder2, nestedPlaceholder3, nestedPlaceholder4))
        .withInputs(newInput1, getInput1(), getInput2(), getInput3(), getInput4());
  }

  @Override
  default PreparableTransformer1<Tuple4<? extends A, ? extends B, ? extends C, ? extends D>, R, ? extends PreparedTransformer1<Tuple4<? extends A, ? extends B, ? extends C, ? extends D>, R>> withArity1(
      Producer<? extends Tuple4<? extends A, ? extends B, ? extends C, ? extends D>> inputTuple) {
    Placeholder<Tuple4<? extends A, ? extends B, ? extends C, ? extends D>> placeholderTuple =
        new Placeholder<>("Original Inputs Tuple");

    return DAG
        .withPlaceholder(placeholderTuple)
        .withOutput(
            withInputs(new Value0FromTuple<>(placeholderTuple), new Value1FromTuple<>(placeholderTuple),
                new Value2FromTuple<>(placeholderTuple), new Value3FromTuple<>(placeholderTuple)))
        .withInput(inputTuple);
  }
}
