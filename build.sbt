import ReleaseTransformations._

Seq(
  // standard options
  scalaVersion           := "2.11.7",
  scalacOptions in Test ++= Seq("-Yrangepos"),

  // release options
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    publishArtifacts,
    setNextVersion,
    commitNextVersion,
    pushChanges
  ),

  // Bintray options
  licenses          += ("Apache-2.0", url("https://github.com/JohnMurray/watson/blob/master/license")),
  publishMavenStyle := true,
  bintrayOrganization in bintray := Some("johnmurray.io")
  // packageLabels in bintray       := Seq("Scala", "Play")
)

// dependencies
libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.6.6" % "test"
)

lazy val watson = Project("watson", file("."))
