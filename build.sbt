name := "E_biznes_sklep"

version := "1.0"

lazy val `e_biznes_sklep` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.12.13"

libraryDependencies ++= Seq(ehcache, ws, specs2 % Test, guice)

libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0" // orm

libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0" // migracje bazy danych

// https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.34.0"
