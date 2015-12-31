package me.tongfei.evalmetric

/**
  * @author Tongfei Chen
  */
object Evaluator {

  /** Returns an object that contains various evaluation metrics of a binary classifier. */
  def binaryClassifier[A](model: A => Int, dataset: Seq[A], gold: Seq[Int]) = {
    val predicted = dataset map model
    BinaryConfusionMatrix(predicted, gold)
  }

  /** Returns an object that contains various evaluation metrics of a multi-class classifier. */
  def classifier[A](model: A => Int, dataset: Seq[A], gold: Seq[Int]) = {
    val predicted = dataset map model
    ConfusionMatrix(predicted, gold)
  }

  /** Returns an object that contains various evaluation metrics of an information retrieval system. */
  def informationRetrievalSystem[A, B](model: A => Seq[B], dataset: Seq[A], gold: Seq[B => Boolean]) = {
    val predicted = dataset map model
    RetrievalRanking(predicted, gold)
  }

}
