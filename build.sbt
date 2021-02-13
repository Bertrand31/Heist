name := "Heist"

version := "1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.4.1",
  "org.typelevel" %% "cats-effect" % "2.3.1",
  // "com.github.bertrand31" %% "pandore" % "0.3",
  "org.scalatest" %% "scalatest" % "3.2.3",
)

scalacOptions ++= Seq(
  "-deprecation", // Warn about deprecated features
  "-encoding", "UTF-8", // Specify character encoding used by source files
  "-feature", // Emit warning for usages of features that should be imported explicitly
  "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
  "-language:higherKinds", // Allow higher-kinded types
  "-unchecked", // Enable additional warnings where generated code depends on assumptions
  "-Xlint:_", // Enable all available style warnings
  "-Ywarn-macros:after", // Only inspect expanded trees when generating unused symbol warnings
  "-Ywarn-unused:_", // Enables all unused warnings
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
)

scalacOptions in Test --= Seq(
  "-Xlint:_",
  "-Ywarn-unused-import",
)

javaOptions ++= Seq(
  "-XX:+CMSClassUnloadingEnabled", // Enable class unloading under the CMS GC
  "-Xms1g",
  "-Xmx12g",
)
