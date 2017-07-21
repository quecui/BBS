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
  specs2 % Test,
//  "org.flywaydb" %% "flyway-play" % "4.0.0",
  "mysql" % "mysql-connector-java" % "5.1.38",

  "org.skinny-framework" %% "skinny-orm"      % "2.3.7",
  "ch.qos.logback"       %  "logback-classic" % "1.1.+",

  "org.scalikejdbc" %% "scalikejdbc"               % "2.5.2",
  "org.scalikejdbc" %% "scalikejdbc-config"           % "2.5.0",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.6.0"
)
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.septeni.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.septeni.binders._"

initialCommands := """
import scalikejdbc._
"""
