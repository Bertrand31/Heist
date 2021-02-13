import org.scalatest.flatspec.AnyFlatSpec
import heist.{BankCoordinate, MathUtils}

class MathUtilsSpec extends AnyFlatSpec {

  behavior of "the probability calculation function"

  it should "apply the relevant forumla to a 25 wide grid" in {

    val probability = MathUtils.calculateProbability(25, 5)
    assert(probability === 0.6738254512314335590792739567338941684151393037899801454013114887)
  }

  it should "apply the relevant forumla to a 100 wide grid" in {

    val probability = MathUtils.calculateProbability(100, 5)
    assert(probability === 0.9756279041567402036467525234746243790962621013547958720229022918)
  }

  it should "return 1 when distance is 0" in {

    val probability = MathUtils.calculateProbability(100, 0)
    assert(probability === 1D)
  }

  behavior of "the euclidian distance calculation function"

  it should "calculate the correct distance between two distinct points" in {

    val a = BankCoordinate(1.3, 3.1)
    val b = BankCoordinate(8.0, 15.9)
    assert(MathUtils.euclidianDistance(a, b) === 14.44749113168096)
  }

  it should "return 0 for two points with the same coordinates" in {

    val a = BankCoordinate(1.3, 3.1)
    val b = BankCoordinate(1.3, 3.1)
    assert(MathUtils.euclidianDistance(a, b) === 0)
  }

  behavior of "the probabilities reduction function"

  it should "calculate the probability of multiple events all occuring" in {

    val probabilities = List(1D/6D, 1D/6D, 1D/6D)
    assert(MathUtils.reduceProbabilities(probabilities) === 0.42129629629629617)
  }
}
