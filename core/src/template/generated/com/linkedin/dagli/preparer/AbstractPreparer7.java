// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the preparer/AbstractPreparerX.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.preparer;

import com.linkedin.dagli.transformer.PreparedTransformer7;
import com.linkedin.dagli.objectio.ObjectReader;


/**
 * A common abstract base class that {@link Preparer}s prepared using an {@link PreparerMode} not known at compile-time
 * are highly encouraged to extend.  If a {@link Preparer} always uses the {@link PreparerMode#BATCH} or
 * {@link PreparerMode#STREAM} modes, it should derive from {@link AbstractBatchPreparer7} or
 * {@link AbstractStreamPreparer7} instead.
 *
 * Besides simplifying the code and improving readability, using the Dagli-provided base class helps insulate the
 * derived class's implementation from future changes in the Dagli Framework.
 *
 * @param <A> the type of the first input
 * @param <B> the type of the second input
 * @param <C> the type of the third input
 * @param <D> the type of the fourth input
 * @param <E> the type of the fifth input
 * @param <F> the type of the sixth input
 * @param <G> the type of the seventh input
 * @param <R> result type of the transformer prepared by this preparer
 * @param <N> the type of {@link com.linkedin.dagli.transformer.PreparedTransformer} prepared by this preparer
 */
public abstract class AbstractPreparer7<A, B, C, D, E, F, G, R, N extends PreparedTransformer7<A, B, C, D, E, F, G, R>>
    extends AbstractPreparer<R, N> implements Preparer7<A, B, C, D, E, F, G, R, N> {

  @Override
  public final void processUnsafe(Object[] values) {
    Preparer7.super.processUnsafe(values);
  }

  @Override
  public final PreparerResultMixed<? extends PreparedTransformer7<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? extends R>, N> finishUnsafe(
      ObjectReader<Object[]> inputs) {
    return Preparer7.super.finishUnsafe(inputs);
  }
}
