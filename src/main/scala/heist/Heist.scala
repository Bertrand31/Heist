package heist

import cats.effect.{ExitCode, IO, IOApp}
import utils.MapUtils

object Heist extends IOApp {

  private def prettyPrintDouble(double: Double): IO[Unit] = {
    val rounded = math.ceil(double * 1e3) / 1e3
    IO { println(rounded) }
  }

  def run(args: List[String]): IO[ExitCode] =
    args
      .headOption
      .map(
        MapUtils
          .loadMap(_)
          .map(HeistAlgorithm.getOverallDetectionProability)
          .flatMap(prettyPrintDouble)
      )
      .getOrElse(IO.raiseError(new IllegalArgumentException("Need an input path for the map")))
      .as(ExitCode.Success)
}
