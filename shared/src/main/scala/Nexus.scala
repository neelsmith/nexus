package edu.holycross.shot.nexus

/* A class implementing the generic structures of the NEXUS file format:
* work with named blocks, and NEXUS commands within blocks.
* Additionally provides some higher-level abstraction of especially common
* or important NEXUS structures such as the DATA block and Matrix command.
*
* See discussions and examples at:
* http://wiki.christophchamp.com/index.php?title=NEXUS_file_format
* http://hydrodictyon.eeb.uconn.edu/eebedia/index.php/Phylogenetics:_NEXUS_Format
*/




/* Some importat blocks that might be worth coding high-order methods for:

*/

import wvlet.log._
import scala.scalajs.js.annotation._
import scala.annotation.tailrec

@JSExportAll
case class Nexus(nexusString: String) extends LogSupport {


  /** Nexus format is line-oriented, so work with
  * data as a Vector of Strings.
  */
  val lines = nexusString.split("\n").toVector

  require(lines.head.toLowerCase.startsWith("#nexus"), "Invalid NEXUS format: required header line '#NEXUS' missing.")


  /** Identify all block names in this Nexus.
  * Lines identifying block names are of the form "BEGIN <blockname> ;"
  * The literal string BEGIN is case insensitive.  The lines may have leading and/or trailing white space.
  * So we find those lines and strip leading and trailing components with two regular expressions
  * to leave the block names as our output.
  */
  def blockNames: Vector[String] = {
    val blockBeginnings = lines.filter(_.trim.toLowerCase.startsWith("begin"))
    blockBeginnings.map(ln => ln.replaceFirst("(?i)[ ]*begin[ ]+", "").replaceFirst(";[ ]*$",""))
  }


  // Find command names within a block
  def commandNames(blockName: String) : Vector[String] = {
    val textChunks = block(blockName).split(";").toVector
    textChunks.map(d => d.trim.replaceFirst("[ ].+", "").replaceFirst("[\\W].*", "")).distinct.filter(_.nonEmpty)
  }

  /** Extract a Nexus command from a Nexus block.
  *
  * @param blockName Name of block.
  * @param commandName Name of command to extract.
  */
  def commandLines(blockName: String, commandName: String): Vector[String] = {
    extractLines(linesForBlock(blockName), commandName, ";")

  }

  /** Extract unaltered text contents for a command within a block.
  *
  * @param blockName Name of block.
  * @param commandName Name of command.
  */
  def command(blockName: String, commandName: String): String = {
    commandLines(blockName, commandName).mkString("\n")
  }

  /** Extract lines for the data block.*/
  def dataLines : Vector[String] = linesForBlock("data")

  /** Extract unaltered contents of DATA block.*/
  def dataBlock : String = block("data")

  /** Extract unaltered text contents of a labelled block.
  *
  * @param blockLabel Label of block to extract.
  */
  def block(blockLabel: String) : String = {
    val matches = linesForBlockMulti(blockLabel, true)
    if (matches.size != 1) {
      throw new Exception("Error: found more than one block named " + blockLabel)
    } else {
      matches.head.mkString("\n")
    }
    //linesForBlock(blockLabel, true).mkString("\n")
  }



  def blocks(blockLabel: String) : String = {
    linesForBlockMulti(blockLabel, true).map(bl => bl.mkString("\n")).mkString("\n")
  }

  /** Extract lines from source data contained with a named block.
  *
  * @param blockLabel Label of block to extract.
  * @param includeComments True if comment lines should be included.
  */
  def linesForBlock(blockLabel: String, includeComments: Boolean = false) : Vector[String] = {
    linesForBlock(blockLabel, lines, includeComments )
  }
  /**  Extract lines from source data contained with a named block.
  *
  * @param blockLabel Label of block to extract.
  * @param srcLines Lines of source data to extract from.
  * @param includeComments True if comment lines should be included.
  */
  def linesForBlock(blockLabel: String,
    srcLines: Vector[String],
    includeComments: Boolean): Vector[String] = {

    val blockInit = "begin " + blockLabel.toLowerCase + ";"
    val blockEnd = "end;"
    // remove comments here if needed
    extractLines(srcLines, blockInit, blockEnd)
  }


  def linesForBlockMulti(blockLabel: String, includeComments: Boolean = false) : Vector[Vector[String]] = {
    linesForBlockMulti(blockLabel, lines, includeComments )
  }
  def linesForBlockMulti(blockLabel: String,
    srcLines: Vector[String],
    includeComments: Boolean): Vector[Vector[String]] = {

    val blockInit = "begin " + blockLabel.toLowerCase + ";"
    val blockEnd = "end;"
    // remove comments here if needed
    Nexus.extractLinesMulti(srcLines, blockInit, blockEnd)
  }

  @tailrec final def extractLines(srcLines: Vector[String],
    blockInit: String,
    blockEnd: String,
    results: Vector[String] = Vector.empty[String],
    inBlock: Boolean = false): Vector[String] = {
      if (srcLines.isEmpty) {
        // Got to end of input without identifying correctly structured block!
        inBlock match {
          case false => throw new Exception("Did not find beginning label for unit " + blockInit)
          case true => throw new Exception("Did not find endinglabel for unit " + blockInit)
        }

      } else if (inBlock && srcLines.head.toLowerCase.trim ==  blockEnd) {
        // Found end of block:
        results

      } else {
        if (srcLines.head.toLowerCase.trim == blockInit) {
          // Found block beginning:
          extractLines(srcLines.tail, blockInit, blockEnd , results, true)

        } else {
          if (inBlock) {
            // Accumulate results:
            val newResults = results :+ srcLines.head
            extractLines(srcLines.tail, blockInit, blockEnd, newResults, inBlock)

          } else {
            // ignore
            extractLines(srcLines.tail, blockInit, blockEnd, results, inBlock)
          }
        }
      }
  }
}


object Nexus extends LogSupport {
   Logger.setDefaultLogLevel(LogLevel.INFO)
    /** Recursively extract lines from a list of lines contained
    * by a given opening/closing patterns. Opening/closing patterns are the
    * case-insensitive contents of a line that may be preceded or followed by white space.
    *
    * @param srcLines List of lines to process.
    * @param blockInit Opening pattern.
    * @param blockEnd Closing pattern.
    * @param results Accumulated lines to keep so far.
    * @param inBlock True if we have seen opening pattern.
    */
  @tailrec final def extractLinesMulti(srcLines: Vector[String],
      blockInit: String,
      blockEnd: String,
      current: Vector[String] = Vector.empty[String],
      results: Vector[Vector[String]] = Vector.empty[Vector[String]],
      inBlock: Boolean = false): Vector[Vector[String]] = {
        debug(s"inblock? ${inBlock}")
        if (srcLines.isEmpty) {
          results

        } else if (inBlock && srcLines.head.trim.toLowerCase.endsWith(blockEnd.toLowerCase)) {
          // Found end of block:
          val newVal = srcLines.head.trim.replaceFirst("(?i)" + blockEnd,"")

          val newCurrent = newVal match {
            case "" => current
            case _ => current :+ newVal
          }

          val newResults = results :+ newCurrent
          extractLinesMulti(srcLines.tail, blockInit, blockEnd , Vector.empty[String], newResults, false)

        } else {
          debug(s"blockInit: ${blockInit}\nLine: #${srcLines.head.trim}#")
          if (srcLines.head.trim.toLowerCase.startsWith(blockInit.toLowerCase)) {
            debug("FOUND BLOCK BEGIN")
            // Found block beginning:
            val newVal = srcLines.head.trim.replaceFirst("(?i)" + blockInit,"")
            val newSrcLines = newVal match {
              case "" => srcLines.tail
              case _ => newVal +: srcLines.tail
            }

            debug("Found init, prepending new Val " + newVal)
            extractLinesMulti(newSrcLines, blockInit, blockEnd, current, results, true)

          } else {
            debug("NOT in block begin")
            if (inBlock) {
              // Accumulate results:

              //val newResults = results :+ srcLines.head
              val newCurrent = current :+ srcLines.head
              extractLinesMulti(srcLines.tail, blockInit, blockEnd, newCurrent, results, inBlock)

            } else {
              // ignore
              extractLinesMulti(srcLines.tail, blockInit, blockEnd, current, results, inBlock)
            }
          }
        }
    }
}
