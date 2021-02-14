import org.scalatest.flatspec.AnyFlatSpec
import heist._

class HeistAlgorithmSpec extends AnyFlatSpec {

  behavior of "the heatmap creation function"

  val bankMap = BankMap(
    width = 25,
    detectors = List(
      FloorCoordinate(2.423929917008996D, 20.187139309438546D),
      FloorCoordinate(1.3175678970133191D, 10.019351994529405D),
      FloorCoordinate(1.0536920857525445D, 2.8936703202385115D),
    )
  )

  it should "create a correct heatmap" in {

    val heatMap = HeistAlgorithm.createHeatMap(bankMap, 5)

    val expectedHeatmap =
      Vector(
        Vector(
          0.9716422216196359, 0.8789138935959622, 0.42010182814385,
          0.07863888909098904, 0.006122174333642039,
        ),
        Vector(
          0.9875934609880566, 0.8969750676209121, 0.4403181677518382,
          0.08664120593117253, 0.0070581408204912766,
        ),
        Vector(
          0.8750767430707469, 0.8275862676805059, 0.43170833734798575,
          0.091248327381106, 0.008031036133162917,
        ),
      )
    assert(heatMap === expectedHeatmap)
  }

  behavior of "the detectionProbability function"

  it should "compute the detection probability of a given point" in {

    val point = FloorCoordinate(18.423929917008996D, 4D)
    val probability = HeistAlgorithm.detectionProbability(bankMap, point)
    assert(probability === 0.01414689382562151)
  }

  it should "return 1 when we're on a detector" in {

    val point = FloorCoordinate(2.423929917008996D, 20.187139309438546D)
    val probability = HeistAlgorithm.detectionProbability(bankMap, point)
    assert(probability === 1)
  }

  behavior of "the getOverallDetectionProability function"

  it should "return the expected probability" in {

    val result = HeistAlgorithm.getOverallDetectionProability(bankMap)
    assert(result === 0.2689393457737861)
  }
}
