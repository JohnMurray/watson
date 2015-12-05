Seq(
  scalaVersion      := "2.11.7",


  // Bintray options
  licenses          += ("Apache-2.0", url("https://github.com/JohnMurray/watson/blob/master/license")),
  publishMavenStyle := true,
  bintrayOrganization in bintray := Some("johnmurray.io"),
  packageLabels in bintray       := Seq("Scala", "Play")
)

lazy val watson = Project("watson", file("."))
