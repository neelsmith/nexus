package edu.holycross.shot.nexus

//import scala.scalajs.js
import wvlet.log._

// JVM only:
// import wvlet.log.LogFormatter.SourceCodeLogFormatter


import scala.scalajs.js.annotation._

@JSExportAll
case class Nexus(nexusString: String) extends LogSupport {
  val lines = nexusString.split("\n")
  require(lines.head.toLowerCase.startsWith("#nexus"), "Invalid NEXUS format: required header line '#NEXUS' missing.")

}

// See discussions at
// http://wiki.christophchamp.com/index.php?title=NEXUS_file_format
// http://hydrodictyon.eeb.uconn.edu/eebedia/index.php/Phylogenetics:_NEXUS_Format
