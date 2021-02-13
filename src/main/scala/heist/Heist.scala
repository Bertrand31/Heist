package heist

import cats.effect.{ExitCode, IO, IOApp}

object KafkaRavane extends IOApp {

  type HeatMap = IndexedSeq[IndexedSeq[Double]]

  private def detectionProbability(bankMap: BankMap, coordinates: BankCoordinate): Double = {
    val BankMap(width, detectors) = bankMap
    val probabilities =
      detectors
        .map(MathUtils.euclidianDistance(coordinates, _))
        .map(MathUtils.calculateProbability(width, _))
    MathUtils.reduceProbabilities(probabilities)
  }

  private def solveProblem(bankMap: BankMap): Double = {
    val startProbability =
      detectionProbability(bankMap, BankCoordinate(bankMap.width / 2, 0))
    val endProbability =
      detectionProbability(bankMap, BankCoordinate(bankMap.width / 2, bankMap.width))
    val heatMap = createHeatMap(bankMap, 100)
    val pathProbailities = heatMap.map(_.min)
    println(startProbability)
    println(endProbability)
    val fullPath = startProbability +: pathProbailities :+ endProbability
    MathUtils.reduceProbabilities(fullPath.toList)
  }

  private def createHeatMap(bankMap: BankMap, steps: Int): HeatMap = {
    val stepSize = bankMap.width / steps
    (1 to steps).map(row =>
      (1 to steps).map(column => {
        val coordinate = BankCoordinate(column * stepSize, row * stepSize)
        detectionProbability(bankMap, coordinate)
      })
    )
  }

  def run(args: List[String]): IO[ExitCode] =
    args
      .headOption
      .map(
        MapUtils.loadMap(_)
          .map(solveProblem)
          .flatMap(solution => IO { println(solution) })
      )
      .getOrElse(IO.raiseError(new IllegalArgumentException("Need an input path for the map")))
      .as(ExitCode.Success)
}
