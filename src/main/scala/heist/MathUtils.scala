package heist

object MathUtils {

  def calculateProbability(width: Double, distance: Double): Double =
    math.exp(-math.pow(math.Pi * (distance / width), 2))

  def euclidianDistance(a: BankCoordinate, b: BankCoordinate): Double =
    math.sqrt(math.pow((b.x - a.x), 2) + math.pow((b.y - a.y), 2))

  val reduceProbabilities: Seq[Double] => Double =
    1 - _.map(1 - _).reduce(_ * _)
}
