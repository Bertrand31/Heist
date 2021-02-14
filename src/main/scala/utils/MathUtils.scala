package heist

object MathUtils {

  import math._

  def calculateProbability(width: Double, distance: Double): Double =
    exp(-pow(Pi * (distance / width), 2))

  def euclidianDistance(a: BankCoordinate, b: BankCoordinate): Double =
    sqrt(pow(b.x - a.x, 2) + pow(b.y - a.y, 2))

  val reduceProbabilities: Seq[Double] => Double =
    1 - _.map(1 - _).reduce(_ * _)
}
