import sbt._
import Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys

object LibraryBuild extends Build {

  val appName = "scala-ui-javafx"
  val appVersion = "1.0"

  val appDependencies = Seq(
    "org.specs2" % "specs2_2.10" % "1.13" % "test",
    "org.mockito" % "mockito-all" % "1.9.5" % "test")

  val defaultSettings = Seq(
    libraryDependencies ++= appDependencies,
    scalaVersion := "2.10.1",
    scalacOptions += "-feature",
    //required for the javaOptions to be passed in
    fork := true,
    resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
      "releases" at "http://oss.sonatype.org/content/repositories/releases"),
    javaOptions in (Test) += "-Djavafx.toolkit=com.sun.javafx.pgstub.StubToolkit"//,
    //javaOptions in (Test) += "-Xdebug",
    //javaOptions in (Test) += "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
    )

  val eclipseSettings = Seq(
    EclipseKeys.withSource := true,
    unmanagedSourceDirectories in Compile <<= Seq(scalaSource in Compile).join)

  lazy val root = Project(id = appName,
    base = file("."),
    settings =
      Project.defaultSettings ++
        defaultSettings ++
        eclipseSettings) dependsOn (scalaUiProject % "test->test;compile->compile")

  lazy val scalaUiProject = RootProject(file("../scala-ui"))
}

