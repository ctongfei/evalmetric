package me.tongfei.evalmetric

/**
  * @author Tongfei Chen
  */
class RetrievalRanking[T] private(val retrieved: Seq[Seq[T]], val relevant: Seq[T => Boolean]) {

  private def rr(top: Seq[T], rel: T => Boolean): Double = {
    for ((cand, rank) ← top.zipWithIndex)
      if (rel(cand))
        return 1.0 / (rank + 1)
    0.0
  }

  private def ap(top: Seq[T], rel: T => Boolean): Double = {
    var n = 0
    var sum = 0.0
    for ((cand, rank) ← top.zipWithIndex) {
      if (rel(cand)) {
        n += 1
        sum += n.toDouble / (rank + 1)
      }
    }
    if (n == 0) 0.0 else sum / n
  }

  private def pAtK(k: Int)(top: Seq[T], rel: T => Boolean): Double = {
    var n = 0
    for (cand ← top.take(k))
      if (rel(cand)) n += 1
    n.toDouble / k
  }

  private def mean(f: (Seq[T], T => Boolean) => Double): Double = {
    var n = 0
    var sum = 0.0
    for ((top, rel) ← retrieved zip relevant) {
      sum += f(top, rel)
      n += 1
    }
    if (sum == 0.0) 0.0
    else sum / n
  }

  /** Returns the precision @ ''k''. */
  def precisionAtK(k: Int) = mean(pAtK(k))

  /** Returns the mean reciprocal rank (MRR). */
  def meanReciprocalRank = mean(rr)

  /** Returns the mean average preciison (MAP). */
  def meanAveragePrecision = mean(ap)

}

object RetrievalRanking {

  def apply[T](predicted: Seq[Seq[T]], gold: Seq[T => Boolean]) =
    new RetrievalRanking(predicted, gold)

}
