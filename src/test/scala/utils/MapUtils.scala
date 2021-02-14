import org.scalatest.flatspec.AnyFlatSpec
import heist._

class MapUtilsSpec extends AnyFlatSpec {

  behavior of "the map loading utils"

  it should "load and parse the sample map correctly" in {

    val map = MapUtils.loadMap("src/test/resources/bank.map").unsafeRunSync
    val expectedMap = BankMap(
      width = 25,
      detectors = List(
        BankCoordinate(2.423929917008996D, 20.187139309438546D),
        BankCoordinate(19.39788132776695D, 14.174570106439353D),
        BankCoordinate(1.3175678970133191D, 10.019351994529405D),
        BankCoordinate(1.0536920857525445D, 2.8936703202385115D),
        BankCoordinate(16.739302303324447D, 15.87541372165791D),
      )
    )
    assert(map === expectedMap)
  }
}
