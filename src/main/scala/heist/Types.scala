package heist

final case class FloorCoordinate(x: Double, y: Double)

final case class BankMap(width: Double, detectors: List[FloorCoordinate])
