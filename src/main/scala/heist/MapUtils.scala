package heist

import java.io.File
import scala.io.Source
import cats.effect.IO

object MapUtils {

  private def loadFile(path: String): IO[IterableOnce[String]] =
    IO { Source.fromFile(new File(path)).getLines() }

  def loadMap(path: String): IO[BankMap] =
    loadFile(path).map(mapDefinition => {
      val head +: tail = mapDefinition.iterator.to(List)
      val coordinates =
        tail
          .tail
          .map(_.split(' '))
          .collect({ case Array(x, y) => BankCoordinate(x.toDouble, y.toDouble) })
      BankMap(head.toFloat, coordinates)
    })
}
