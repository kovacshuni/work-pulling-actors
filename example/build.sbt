organization := """com.hunorkovacs"""

name := """work-pulling-actors-example"""

version := "1.0.1-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= List(
  "com.hunorkovacs" %% "work-pulling-actors" % "1.0.0-SNAPSHOT",
  "org.slf4j" % "slf4j-api" % "1.7.12",
  "ch.qos.logback" % "logback-classic" % "1.1.3"
)
