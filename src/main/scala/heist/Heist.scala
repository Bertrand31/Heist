package heist

import scala.util.chaining.scalaUtilChainingOps
import cats.effect.{ExitCode, IO, IOApp}

object KafkaRavane extends IOApp {

  type HeatMap = IndexedSeq[IndexedSeq[Double]]

  private val Precision = 100

  private def detectionProbability(bankMap: BankMap, coordinates: BankCoordinate): Double =
    bankMap
      .detectors
      .map(MathUtils.euclidianDistance(coordinates, _))
      .map(MathUtils.calculateProbability(bankMap.width, _))
      .pipe(MathUtils.reduceProbabilities)

  private def solveProblem(bankMap: BankMap): Double = {
    val startProbability =
      detectionProbability(bankMap, BankCoordinate(bankMap.width / 2, 0))
    val endProbability =
      detectionProbability(bankMap, BankCoordinate(bankMap.width / 2, bankMap.width))
    val heatMap = createHeatMap(bankMap, Precision)
    val pathProbailities = heatMap.map(_.min)
    val fullPath = startProbability +: pathProbailities :+ endProbability
    MathUtils.reduceProbabilities(fullPath)
  }

  private def createHeatMap(bankMap: BankMap, steps: Int): HeatMap = {
    val stepSize = bankMap.width / steps
    (1 until (steps - 1)).map(row =>
      (1 until (steps - 1)).map(column => {
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
