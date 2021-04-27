name := "E_biznes_sklep"
 
version := "1.0" 
      
lazy val `e_biznes_sklep` = (project in file(".")).enablePlugins(PlayScala)

      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.13"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )
      