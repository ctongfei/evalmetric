package me.tongfei.evalmetric

/**
  * @author Tongfei Chen
  */
class ConfusionMatrix (val n: Int) { self =>

  private val matrix = Array.ofDim[Int](n, n)
  private var all = 0


  /**
    * Returns the number of samples that are <ul>
    *     <li> Predicted as ''i''; </li>
    *     <li> Bears the real label ''j''. </li>
    *   </ul>
    */
  def apply(i: Int, j: Int) = matrix(i)(j)

  def accuracy = {
    val s = (0 until n).map(i => this(i, i)).sum
    if (s == 0) 0.0
    else s.toDouble / all
  }

  def project(k: Int): BinaryConfusionMatrix = {
    val predictedAsK = (0 until n).map(i => this(k, i)).sum
    val trueIsK = (0 until n).map(i => this(i, k)).sum
    val correct = this(k, k)
    val bcm = Array.ofDim[Int](2, 2)
    bcm(0)(0) = all - predictedAsK - trueIsK + correct
    bcm(0)(1) = trueIsK - correct
    bcm(1)(0) = predictedAsK - correct
    bcm(1)(1) = correct
    new BinaryConfusionMatrix {
      matrix = bcm
      all = self.all
    }
  }

}

object ConfusionMatrix {

  def apply(predictedResults: Seq[Int], trueResults: Seq[Int]) = {
    val cm = new ConfusionMatrix((predictedResults.max max trueResults.max) + 1)
    for ((p, t) ← predictedResults zip trueResults) {
      cm.matrix(p)(t) += 1
      cm.all += 1
    }
    cm
  }

}
