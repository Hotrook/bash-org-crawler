name := "bash-org-crawler"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"
libraryDependencies += "com.typesafe" % "config" % "1.3.3"
libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.1.0"
libraryDependencies += "net.sourceforge.htmlunit" % "htmlunit" % "2.33"

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.25" % Test
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.25" % Test

libraryDependencies += "net.jadler" % "jadler-jdk" % "1.3.0" % Test
libraryDependencies += "net.jadler" % "jadler-core" % "1.3.0" % Test

libraryDependencies += "com.google.code.gson" % "gson" % "2.8.5"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.11"

