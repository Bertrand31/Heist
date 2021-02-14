package heist

import scala.util.chaining.scalaUtilChainingOps
import utils.MathUtils

object HeistAlgorithm {

  type HeatMap = IndexedSeq[IndexedSeq[Double]]

  private val Precision = 10

  def detectionProbability(bankMap: BankMap, coordinates: FloorCoordinate): Double =
    bankMap
      .detectors
      .map(MathUtils.euclidianDistance(coordinates, _))
      .map(MathUtils.calculateProbability(bankMap.width, _))
      .pipe(MathUtils.reduceProbabilities)

  def createHeatMap(bankMap: BankMap, steps: Int): HeatMap = {
    val stepSize = bankMap.width / steps
    // The top and bottom rows need not be generated: we know which point we're starting from,
    // and which point we're ending at. Columns however, have to go from edge to edge.
    (1 until (steps - 1)).map(row =>
      (0 until steps).map(column => {
        val coordinate = FloorCoordinate(column * stepSize, row * stepSize)
        detectionProbability(bankMap, coordinate)
      })
    )
  }

  def getOverallDetectionProability(bankMap: BankMap): Double = {
    val entrance = FloorCoordinate(bankMap.width / 2, 0)
    val startProbability = detectionProbability(bankMap, entrance)
    val vault = FloorCoordinate(bankMap.width / 2, bankMap.width)
    val endProbability = detectionProbability(bankMap, vault)
    val heatMap = createHeatMap(bankMap, Precision)
    val pathProbailities = heatMap.map(_.min)
    val fullPath = startProbability +: pathProbailities :+ endProbability
    MathUtils.reduceProbabilities(fullPath)
  }
}
