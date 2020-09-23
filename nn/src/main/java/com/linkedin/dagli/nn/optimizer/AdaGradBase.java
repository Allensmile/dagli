package com.linkedin.dagli.nn.optimizer;

import com.linkedin.dagli.annotation.struct.Optional;
import com.linkedin.dagli.annotation.struct.Struct;
import com.linkedin.dagli.annotation.struct.TrivialPublicConstructor;
import com.linkedin.dagli.annotation.visitor.VisitedBy;


@Struct("AdaGrad")
@TrivialPublicConstructor
@VisitedBy("OptimizerVisitor")
abstract class AdaGradBase extends Optimizer {
  private static final long serialVersionUID = 1;

  @Optional
  double _learningRate = 0.01;
}
