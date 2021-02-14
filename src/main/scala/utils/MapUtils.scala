package utils

import java.io.File
import scala.io.Source
import cats.effect.IO
import heist.{FloorCoordinate, BankMap}

object MapUtils {

  private def loadFile(path: String): IO[IterableOnce[String]] =
    IO { Source.fromFile(new File(path)).getLines() }

  def loadMap(path: String): IO[BankMap] =
    loadFile(path).map(mapDescription => {
      val head +: _ +: detectors = mapDescription.iterator.to(List)
      val coordinates =
        detectors
          .map(_.split(' '))
          .collect({ case Array(x, y) => FloorCoordinate(x.toDouble, y.toDouble) })
      BankMap(head.toDouble, coordinates)
    })
}
