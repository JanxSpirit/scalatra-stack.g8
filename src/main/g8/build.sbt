import Wro4jKeys._

organization := "com.janxspirit"

name := "Scalatra Stack"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.2"

seq(webSettings :_*)

classpathTypes ~= (_ + "orbit")

libraryDependencies ++= Seq(
  "org.scalatra" % "scalatra" % "2.2.0-RC1",
  "org.scalatra" % "scalatra-scalate" % "2.2.0-RC1",
  "org.scalatra" % "scalatra-specs2" % "2.2.0-RC1" % "test",
  "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "container",
  "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
)

seq(wro4jSettings: _*)

compile in Compile <<= (compile in Compile).dependsOn(generateResources in Compile)

(webappResources in Compile) <+= (targetFolder in generateResources in Compile)

(warPostProcess in Compile) <<= (target in Compile) map { tgt =>
  val webapp = tgt / "webapp"
  () => {
    // val files = Seq(webapp / "WEB-INF" / "lib", webapp / "js", webapp / "css", webapp / "WEB-INF" / "classes")
    val files = Seq(webapp / "js", webapp / "css")
    IO.delete(files)
  }
}

outputFolder in (Compile, generateResources) := "assets/"

processorProvider in (Compile, generateResources) := new OAuth2Processors

wroFile in (Compile, generateResources) <<= (baseDirectory)(_ / "project" / "wro.xml")

propertiesFile in (Compile, generateResources) <<= (baseDirectory)(_ / "project" / "wro.properties")

watchSources <++= (sourceDirectory in Compile) map (d => (d / "webapp" ** "*").get)