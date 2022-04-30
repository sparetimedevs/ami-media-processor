import Dependencies._

ThisBuild / organization := "com.sparetimedevs"
ThisBuild / version := "0.0.1"
ThisBuild / scalaVersion := "3.1.1"
ThisBuild / scalacOptions ++= Seq(
  "-Yexplicit-nulls",
  "-Ysafe-init"
)

lazy val testUtil = (project in file("test-util"))
  .settings(
    name := "test-util",
    libraryDependencies ++= Seq(catsEffect, scalaTest)
  )

lazy val core = (project in file("ami-core"))
  .settings(
    name := "ami-core",
    libraryDependencies ++= Seq(scalaTest, musicProtobuf)
  )
  .dependsOn(testUtil)
  .aggregate(testUtil)

lazy val root = (project in file("."))
  .settings(
    name := "ami-media-processor",
    libraryDependencies ++= Seq(
      scalaXml,
      scalaParser,
      jaxbApi,
      catsCore,
      catsEffect,
      doodle,
      scalaTest,
      diffUtils
      /* , scalatestSnapshotMatcherCore included in /lib/scalatest-snapshot-matcher-core_3.jar */
    )
  )
  .dependsOn(testUtil, core)
  .aggregate(testUtil, core)
