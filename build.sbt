import Dependencies._

ThisBuild / scalaVersion     := "2.11.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.thiefspin"
ThisBuild / organizationName := "thiefspin"

lazy val root = (project in file("."))
  .settings(
    name := "snake-game",
    libraryDependencies += scalaTest % Test
  ).enablePlugins(ScalaNativePlugin)

