// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the preparer/AbstractPreparerX.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.preparer;

import com.linkedin.dagli.transformer.PreparedTransformer8;
import com.linkedin.dagli.objectio.ObjectReader;


/**
 * A common abstract base class that {@link Preparer}s prepared using an {@link PreparerMode} not known at compile-time
 * are highly encouraged to extend.  If a {@link Preparer} always uses the {@link PreparerMode#BATCH} or
 * {@link PreparerMode#STREAM} modes, it should derive from {@link AbstractBatchPreparer8} or
 * {@link AbstractStreamPreparer8} instead.
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
 * @param <H> the type of the eighth input
 * @param <R> result type of the transformer prepared by this preparer
 * @param <N> the type of {@link com.linkedin.dagli.transformer.PreparedTransformer} prepared by this preparer
 */
public abstract class AbstractPreparer8<A, B, C, D, E, F, G, H, R, N extends PreparedTransformer8<A, B, C, D, E, F, G, H, R>>
    extends AbstractPreparer<R, N> implements Preparer8<A, B, C, D, E, F, G, H, R, N> {

  @Override
  public final void processUnsafe(Object[] values) {
    Preparer8.super.processUnsafe(values);
  }

  @Override
  public final PreparerResultMixed<? extends PreparedTransformer8<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? extends R>, N> finishUnsafe(
      ObjectReader<Object[]> inputs) {
    return Preparer8.super.finishUnsafe(inputs);
  }
}
