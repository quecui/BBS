name := """bbs_hungpt"""
organization := "com.septeni"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test,
  evolutions,
  jdbc,

  "mysql" % "mysql-connector-java" % "5.1.38",

  "ch.qos.logback"       %  "logback-classic" % "1.1.+",

  "org.scalikejdbc" %% "scalikejdbc"               % "2.5.2",
  "org.scalikejdbc" %% "scalikejdbc-config"           % "2.5.0",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.6.0",

  "org.mockito" % "mockito-all" % "1.8.4"
)

initialCommands := """
import scalikejdbc._
"""
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

import scalariform.formatter.preferences._

val preferences =
  ScalariformKeys.preferences := ScalariformKeys.preferences.value
    .setPreference(AlignSingleLineCaseStatements, true)
    .setPreference(DoubleIndentConstructorArguments, true)
    .setPreference(DanglingCloseParenthesis, Preserve)
SbtScalariform.scalariformSettings ++ Seq(preferences)