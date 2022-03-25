import Dependencies._

ThisBuild / organization := "com.sparetimedevs"
ThisBuild / version := "0.0.1"
ThisBuild / scalaVersion := "3.1.1"

scalacOptions ++= Seq(
  "-Yexplicit-nulls",
  "-Ysafe-init"
)

lazy val root = (project in file("."))
  .settings(
    name := "ami-media-processor",
    libraryDependencies ++= Seq(scalaXml, scalaParser, jaxbApi, doodle, scalaTest, diffUtils /* , scalatestSnapshotMatcherCore included in /lib/scalatest-snapshot-matcher-core_3.jar */ )
  )
