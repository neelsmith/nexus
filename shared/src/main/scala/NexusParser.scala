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

import wvlet.log._
import scala.scalajs.js.annotation._
import scala.annotation.tailrec

@JSExportAll
case class NexusParser(nexusString: String) extends LogSupport {


  /** Nexus format is line-oriented, so work with
  * data as a Vector of Strings.
  */
  val lines = nexusString.split("\n").toVector

  require(lines.head.toLowerCase.startsWith("#nexus"), "Invalid NEXUS format: required header line '#NEXUS' missing.")



  def nexusBlocks: Vector[NexusBlock] = {
    blockNames.map (blockName => {
      val cmdNames = commandNames(blockName)
      val lines = for (cName <- cmdNames) yield {
        command(blockName, cName )
      }

      val nexusCommands = lines.map(ln => {
        val cName = NexusParser.extractCommandName(ln)
        val cmd =  NexusCommand(cName, "DATA HERE")
        cmd
      })
      NexusBlock(blockName, nexusCommands)
    }
    )
  }

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


  /** Find command names within a single block.  Assumes that
  * we can use the [[block]] method to extract a single
  * block of lines.
  *
  * @param blockName Name of block to extract command names from.
  */
  def commandNames(blockName: String) : Vector[String] = {
    val textChunks = block(blockName).split(";").toVector
    val trimmedUp = textChunks.map(chunk => NexusParser.extractCommandName(chunk.trim))
    trimmedUp.distinct.filter(_.nonEmpty)
  }

  /** Chunk the text of a NEXUS block into a series of
  * Nexus commands.
  *
  * @param blockName Name of block to chunk.
  */
  def commands(blockName: String) : Vector[String] = {
     block(blockName).split(";").toVector
  }


  /** Extract all occurrences of a Nexus command from Nexus blocks
  * of a given name.  Vectors of command lines are further grouped in Vectors,
  * one Vector per block.
  *
  * @param blockName Name of block.
  * @param commandName Name of command to extract.
  */
  def commandLines(blockName: String, commandName: String): Vector[Vector[String]] = {
    val commands = for (bl <- linesForBlocks(blockName)) yield {
      val cmds = NexusParser.extractLines(bl, commandName,";")
      cmds
    }
    commands.flatten
  }

  /** Extract unaltered text contents for a command within a block.
  *
  * @param blockName Name of block.
  * @param commandName Name of command.
  */
  def command(blockName: String, commandName: String): String = {
    val nestedLines = commandLines(blockName, commandName)
    val blockString = for (blck <- nestedLines) yield {
      blck.mkString("\n")
    }
    blockString.mkString("\n")
  }

  /** Extract lines for the data block.
  * Assuming NEXUS files can only have one data block?
  */
  def dataLines : Vector[String] = linesForBlock("data")

  /** Extract unaltered contents of DATA block.
  * Assuming NEXUS files can only have one data block?
  */
  def dataBlock : String = block("data")

  /** Extract unaltered text contents of a labelled block
  * that appears only once in the Nexus source.
  *
  * @param blockLabel Label of block to extract.
  */
  def block(blockLabel: String) : String = {
    val matches = linesForBlocks(blockLabel, true)
    if (matches.size != 1) {
      throw new Exception("Error: found more than one block named " + blockLabel)
    } else {
      matches.head.mkString("\n")
    }
  }


  /** Extract unaltered text contents for possibly multiple
  * occurrences of a labelled block.
  *
  * @param blockLabel Label of block to extract.
  */
  def blocks(blockLabel: String) : String = {
    linesForBlocks(blockLabel, true).map(bl => bl.mkString("\n")).mkString("\n")
  }

  /** Extract lines from source data contained with a named block
  * occuring only once in the Nexus source.
  *
  * @param blockLabel Label of block to extract.
  * @param includeComments True if comment lines should be included.
  */
  def linesForBlock(blockLabel: String, includeComments: Boolean = false) : Vector[String] = {
    linesForBlock(blockLabel, lines, includeComments )
  }

  /**  Extract lines from source data contained with a named block
  * occuring only once in the Nexus source.
  *
  * @param blockLabel Label of block to extract.
  * @param srcLines Lines of source data to extract from.
  * @param includeComments True if comment lines should be included.
  */
  def linesForBlock(blockLabel: String,
    srcLines: Vector[String],
    includeComments: Boolean): Vector[String] = {
      val blocks = linesForBlocks(blockLabel, srcLines, includeComments)
      blocks.size match {
        case 0 => Vector.empty[String]
        case 1 => blocks(0)
        case _ => throw new Exception(s"Error: found ${blocks.size} blocks labelled ${blockLabel}")
      }
  }



  /**  Extract all lines from source data contained with blocks
  * of a given name.  Vectors of lines are further grouped in Vectors,
  * one Vector per block.
  *
  * @param blockLabel Label of block to extract.
  * @param includeComments True if comment lines should be included.
  */
  def linesForBlocks(blockLabel: String, includeComments: Boolean = false) : Vector[Vector[String]] = {
    linesForBlocks(blockLabel, lines, includeComments )
  }

  /**  Extract all lines from source data contained with blocks
  * of a given name.  Vectors of lines are further grouped in Vectors,
  * one Vector per block.
  *
  * @param blockLabel Label of block to extract.
  * @param srcLines Lines of Nexus data to extract from.
  * @param includeComments True if comment lines should be included.
  */
  def linesForBlocks(blockLabel: String,
    srcLines: Vector[String],
    includeComments: Boolean): Vector[Vector[String]] = {

    val blockInit = "begin " + blockLabel.toLowerCase + ";"
    val blockEnd = "end;"
    // remove comments here if needed
    NexusParser.extractLines(srcLines, blockInit, blockEnd)
  }

}

/** The Nexus object provides some string processing methods that
* implement specific conventions of the NEXUS file format.
*/
object NexusParser extends LogSupport {


  /** Recursively extract the command name from a Nexus command chunk
  * character by character.
  *
  * @param src Command string.
  * @param result Characters extracted so far.
  */
  @tailrec final def extractCommandName(src: String, result: String = "") : String = {
    if (src.isEmpty) {
      result
    } else if (src.head.isWhitespace) {
      result
    } else {
      extractCommandName(src.tail, result :+ src.head)
    }
  }

  /** Recursively extract lines from a list of lines contained
  * by a given opening/closing patterns. Opening/closing patterns are the
  * case-insensitive contents of a line that may be preceded or followed by white space.
  *
  * @param srcLines List of lines to process.
  * @param blockInit Opening pattern.
  * @param blockEnd Closing pattern.
  * @param current Accumulated lines in current block.
  * @param results Vectors of accumulated lines for each block.
  * @param inBlock True if we have seen opening pattern.
  */
  @tailrec final def extractLines(srcLines: Vector[String],
      blockInit: String,
      blockEnd: String,
      current: Vector[String] = Vector.empty[String],
      results: Vector[Vector[String]] = Vector.empty[Vector[String]],
      inBlock: Boolean = false): Vector[Vector[String]] = {
        debug(s"inblock? ${inBlock}")
        if (srcLines.isEmpty) {
          results

        } else if (inBlock && srcLines.head.trim.toLowerCase.endsWith(blockEnd.toLowerCase)) {
          // Found end-of-block pattern. Check for a value preceding it
          // in the line:
          val newVal = srcLines.head.trim.replaceFirst("(?i)" + blockEnd,"")
          val newCurrent = newVal match {
            case "" => current
            case _ => current :+ newVal
          }

          val newResults = results :+ newCurrent
          extractLines(srcLines.tail, blockInit, blockEnd , Vector.empty[String], newResults, false)

        } else {
          debug(s"blockInit: ${blockInit}\nLine: #${srcLines.head.trim}#")
          if (srcLines.head.trim.toLowerCase.startsWith(blockInit.toLowerCase)) {
            debug("FOUND BLOCK BEGIN")
            // Found block beginning. Check for a value following it
            // in the line:
            val newVal = srcLines.head.trim.replaceFirst("(?i)" + blockInit,"")
            val newSrcLines = newVal match {
              case "" => srcLines.tail
              case _ => newVal +: srcLines.tail
            }

            debug("Found init, prepending new Val " + newVal)
            extractLines(newSrcLines, blockInit, blockEnd, current, results, true)

          } else {
            debug("NOT in block begin")
            if (inBlock) {
              // Add line to accumulated results:
              val newCurrent = current :+ srcLines.head
              extractLines(srcLines.tail, blockInit, blockEnd, newCurrent, results, inBlock)

            } else {
              // Not in block: ignore line
              extractLines(srcLines.tail, blockInit, blockEnd, current, results, inBlock)
            }
          }
        }
    }
}
