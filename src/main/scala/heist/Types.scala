package heist

final case class BankCoordinate(x: Double, y: Double)

final case class BankMap(width: Float, detectors: List[BankCoordinate])
