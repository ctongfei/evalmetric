package me.tongfei.evalmetric

/**
  * Represents a binary confusion matrix.
  * @author Tongfei Chen
  * @since 0.1
  */
class BinaryConfusionMatrix {

  private[evalmetric] var matrix = Array.ofDim[Int](2, 2)
  private[evalmetric] var all = 0

  /**
    * Returns the number of samples that are <ul>
    *     <li> Predicted as ''i''; </li>
    *     <li> Bears the real label ''j''. </li>
    *   </ul>
    */
  def apply(i: Int, j: Int) = matrix(i)(j)

  private def tp = matrix(1)(1).toDouble
  private def fp = matrix(1)(0).toDouble
  private def tn = matrix(0)(0).toDouble
  private def fn = matrix(0)(1).toDouble

  def recall = if (tp == 0.0) 0.0
    else tp / (tp + fn)

  def sensitivity = recall

  def precision = if (tp == 0.0) 0.0
    else tp / (tp + fp)

  def specificity = if (tn == 0.0) 0.0
    else tn / (fp + tn)

  def accuracy = if (tp + tn == 0.0) 0.0
  else (tp + tn) / all

  def f1Score = if (tp == 0.0) 0.0
    else 2 * tp / (2 * tp + fp + fn)

}

object BinaryConfusionMatrix {

  def apply(predictedResults: Seq[Int], trueResults: Seq[Int]) = {
    val cm = new BinaryConfusionMatrix
    for ((p, t) ← predictedResults zip trueResults) {
      cm.matrix(p)(t) += 1
      cm.all += 1
    }
    cm
  }

}