package me.tongfei.evalmetric

/**
  * @author Tongfei Chen
  */
object Test extends App {

  val bcm = BinaryConfusionMatrix(Seq(1,1,1,1,1,1,1,1,0), Seq(1,1,1,1,1,1,1,0,0,0,0))

  println(bcm.accuracy)
  println(bcm.precision)
  println(bcm.recall)
  println(bcm.f1Score)

  val cm = ConfusionMatrix(Seq(1,1,2,2,3,3,4), Seq(1,2,2,2,3,4,4))


  val rr = RetrievalRanking(Seq(Seq(1,2,3), Seq(2,3,4)), Seq(Set(2,3), Set(2,3)))
  println(rr.precisionAtK(10))
  println(rr.meanAveragePrecision)
  println(rr.meanReciprocalRank)

}
