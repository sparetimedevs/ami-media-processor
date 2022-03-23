import sbt._

object Dependencies {
  lazy val scalaXml = "org.scala-lang.modules" %% "scala-xml" % "2.0.1"
  lazy val scalaParser = "org.scala-lang.modules" %% "scala-parser-combinators" % "2.0.0"
  lazy val jaxbApi = "javax.xml.bind" % "jaxb-api" % "2.3.1"
  lazy val doodle = "org.creativescala" %% "doodle" % "0.10.1"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.11" % "test"
  lazy val diffUtils = "com.googlecode.java-diff-utils" % "diffutils" % "1.3.0" // needed for scalatest-snapshot-matcher-core dependency, included in /lib/scalatest-snapshot-matcher-core_3.jar
  // lazy val scalatestSnapshotMatcherCore = "com.commodityvectors" %% "scalatest-snapshot-matcher-core" % "2.0.4-SNAPSHOT" % "test" // included in /lib/scalatest-snapshot-matcher-core_3.jar
}
