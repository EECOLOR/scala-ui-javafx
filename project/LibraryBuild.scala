import sbt._
import Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys

object LibraryBuild extends Build {

  val appName = "scala-ui-javafx"
  val appVersion = "1.0"

  val appDependencies = Seq(
    "org.specs2" % "specs2_2.10" % "1.13" % "test")

  val defaultSettings = Seq(
    libraryDependencies ++= appDependencies,
    scalaVersion := "2.10.0",
    scalacOptions += "-feature",
    resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
      "releases" at "http://oss.sonatype.org/content/repositories/releases"))

  val eclipseSettings = Seq(
    EclipseKeys.withSource := true,
    unmanagedSourceDirectories in Compile <<= Seq(scalaSource in Compile).join,
    unmanagedSourceDirectories in Test <<= Seq(scalaSource in Test).join)

  lazy val root = Project(id = appName,
    base = file("."),
    settings =
      Project.defaultSettings ++
        defaultSettings ++
        eclipseSettings) dependsOn scalaUiProject

  lazy val scalaUiProject = RootProject(file("../scala-ui"))
}

