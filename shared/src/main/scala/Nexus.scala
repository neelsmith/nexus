package edu.holycross.shot.nexus

//import scala.scalajs.js
import wvlet.log._

// JVM only:
// import wvlet.log.LogFormatter.SourceCodeLogFormatter


import scala.scalajs.js.annotation._

@JSExportAll
case class Nexus(nexusString: String) extends LogSupport {

  /** Nexus format is line-oriented, so work with
  * data as a Vector of Strings.
  */
  val lines = nexusString.split("\n").toVector

  require(lines.head.toLowerCase.startsWith("#nexus"), "Invalid NEXUS format: required header line '#NEXUS' missing.")

  def linesForBlock(blockLabel: String, includeComments: Boolean = false) : Vector[String] = {
    linesForBlock(blockLabel, lines, Vector.empty[String], false, includeComments )

  }

  /** Extract lines contained within a named block.
  */
  def linesForBlock(blockLabel: String,
    srcLines: Vector[String],
    results: Vector[String],
    inBlock: Boolean,
    includeComments: Boolean): Vector[String] = {

    val hdr = "begin " + blockLabel.toLowerCase + ";"
    if (srcLines.isEmpty) {
      results

    } else if (inBlock && srcLines.head.trim.startsWith("end;")) {
      results

    } else {

      if (srcLines.head.toLowerCase.trim == hdr) {
        linesForBlock(blockLabel, srcLines.tail, results, true, includeComments)


      } else {
        if (inBlock) {
          val newResults = results :+ srcLines.head
          linesForBlock(blockLabel, srcLines.tail, newResults, inBlock, includeComments)

        } else {
          linesForBlock(blockLabel, srcLines.tail, results, inBlock, includeComments)
        }
      }

    }
  }
}

// See discussions at
// http://wiki.christophchamp.com/index.php?title=NEXUS_file_format
// http://hydrodictyon.eeb.uconn.edu/eebedia/index.php/Phylogenetics:_NEXUS_Format
