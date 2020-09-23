// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the dag/AbstractDAGResultX.ftl file.
// See the README in the module's src/template directory for details.
package com.linkedin.dagli.dag;

import com.linkedin.dagli.objectio.ObjectReader;
import com.linkedin.dagli.objectio.WrappedObjectReader;
import java.util.Objects;
import com.linkedin.dagli.tuple.Tuple10;
import com.linkedin.dagli.objectio.tuple.TupleReader;


/**
 * Base class for an object encapsulating the results of executing a DAG.
 *
 * @param <RA> the type of result #1 of the DAG
 * @param <RB> the type of result #2 of the DAG
 * @param <RC> the type of result #3 of the DAG
 * @param <RD> the type of result #4 of the DAG
 * @param <RE> the type of result #5 of the DAG
 * @param <RF> the type of result #6 of the DAG
 * @param <RG> the type of result #7 of the DAG
 * @param <RH> the type of result #8 of the DAG
 * @param <RI> the type of result #9 of the DAG
 * @param <RJ> the type of result #10 of the DAG
 */
abstract class AbstractDAGResult10<RA, RB, RC, RD, RE, RF, RG, RH, RI, RJ> extends
    WrappedObjectReader<Tuple10<RA, RB, RC, RD, RE, RF, RG, RH, RI, RJ>> implements
    DAGResult10<RA, RB, RC, RD, RE, RF, RG, RH, RI, RJ>, AutoCloseable {
  private final ObjectReader<RA> _result1;
  private final ObjectReader<RB> _result2;
  private final ObjectReader<RC> _result3;
  private final ObjectReader<RD> _result4;
  private final ObjectReader<RE> _result5;
  private final ObjectReader<RF> _result6;
  private final ObjectReader<RG> _result7;
  private final ObjectReader<RH> _result8;
  private final ObjectReader<RI> _result9;
  private final ObjectReader<RJ> _result10;

  @Override
  public ObjectReader<RA> getResult1() {
    return _result1;
  }

  @Override
  public ObjectReader<RB> getResult2() {
    return _result2;
  }

  @Override
  public ObjectReader<RC> getResult3() {
    return _result3;
  }

  @Override
  public ObjectReader<RD> getResult4() {
    return _result4;
  }

  @Override
  public ObjectReader<RE> getResult5() {
    return _result5;
  }

  @Override
  public ObjectReader<RF> getResult6() {
    return _result6;
  }

  @Override
  public ObjectReader<RG> getResult7() {
    return _result7;
  }

  @Override
  public ObjectReader<RH> getResult8() {
    return _result8;
  }

  @Override
  public ObjectReader<RI> getResult9() {
    return _result9;
  }

  @Override
  public ObjectReader<RJ> getResult10() {
    return _result10;
  }

  /**
   * Create a new DAG result instance from the provided array of {@link ObjectReader}s.
   *
   * @param results an array of length 10 providing an {@link ObjectReader} for each of the DAG's outputs.
   */
  @SuppressWarnings("unchecked")
  AbstractDAGResult10(ObjectReader<?>[] results) {
    this((ObjectReader<RA>) results[0], (ObjectReader<RB>) results[1], (ObjectReader<RC>) results[2],
        (ObjectReader<RD>) results[3], (ObjectReader<RE>) results[4], (ObjectReader<RF>) results[5],
        (ObjectReader<RG>) results[6], (ObjectReader<RH>) results[7], (ObjectReader<RI>) results[8],
        (ObjectReader<RJ>) results[9]);
    assert results.length == 10;
  }

  /**
   * Create a new DAG result instance from the provided {@link ObjectReader}s.
   *
   * @param results1 an {@link ObjectReader} for the DAG's first output.
   * @param results2 an {@link ObjectReader} for the DAG's second output.
   * @param results3 an {@link ObjectReader} for the DAG's third output.
   * @param results4 an {@link ObjectReader} for the DAG's fourth output.
   * @param results5 an {@link ObjectReader} for the DAG's fifth output.
   * @param results6 an {@link ObjectReader} for the DAG's sixth output.
   * @param results7 an {@link ObjectReader} for the DAG's seventh output.
   * @param results8 an {@link ObjectReader} for the DAG's eighth output.
   * @param results9 an {@link ObjectReader} for the DAG's ninth output.
   * @param results10 an {@link ObjectReader} for the DAG's tenth output.
   */
  AbstractDAGResult10(ObjectReader<RA> results1, ObjectReader<RB> results2, ObjectReader<RC> results3,
      ObjectReader<RD> results4, ObjectReader<RE> results5, ObjectReader<RF> results6, ObjectReader<RG> results7,
      ObjectReader<RH> results8, ObjectReader<RI> results9, ObjectReader<RJ> results10) {
    super(new TupleReader<>(results1, results2, results3, results4, results5, results6, results7, results8, results9,
        results10));

    _result1 = Objects.requireNonNull(results1);
    _result2 = Objects.requireNonNull(results2);
    _result3 = Objects.requireNonNull(results3);
    _result4 = Objects.requireNonNull(results4);
    _result5 = Objects.requireNonNull(results5);
    _result6 = Objects.requireNonNull(results6);
    _result7 = Objects.requireNonNull(results7);
    _result8 = Objects.requireNonNull(results8);
    _result9 = Objects.requireNonNull(results9);
    _result10 = Objects.requireNonNull(results10);
  }

  @Override
  public void close() {
    _result1.close();
    _result2.close();
    _result3.close();
    _result4.close();
    _result5.close();
    _result6.close();
    _result7.close();
    _result8.close();
    _result9.close();
    _result10.close();
  }
}
