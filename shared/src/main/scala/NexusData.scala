package edu.holycross.shot.nexus

/** Object representation of the contents of a NEXUS source file
* as a list of [[NexusBlock]] objects.
*
* @param blocks List of [[NexusBlock]]s in this data set.
*/
case class NexusData(blocks: Vector[NexusBlock]) {


  /** Find list of block names.*/
  def blockNames: Vector[String] = {
    blocks.map(_.label)
  }

  /** Find first block with a given name, as on Option.
  * Note that public blocks are supposed to occur only once, so
  * this works without complication for any public block.  If you're
  * rolling your own repeated private blocks with the same name, you'll
  * have to filter the blocks Vector yourself.
  *
  * @param blockName Name of block to find.
  */
  def block(blockName: String) : Option[NexusBlock] = {
    val matchingName = blocks.filter(_.label.toLowerCase == blockName.toLowerCase)
    matchingName.size match {
      case 0 => None
      case _ => Some(matchingName.head)
    }
  }


  def commandNames(blockName: String): Vector[String] = {
    val blockOption = block(blockName)
    /*val block :  NexusBlock = blockOption match {
      case  Some(nexusBlock)  => nexusBlock
      case None => throw new Exception("No block found for " + blockName)
    }*/
    blockOption.get.commandNames
  }

  /**
  */
  def matrix(blockName: String) : NexusMatrix = {
    val cmds = block(blockName).get.commands.filter(_.commandName.toLowerCase == "matrix").head.argsString
    val commandLines = cmds.split("\n").toVector.map(_.trim)

    val nexusCharacters = commandLines.map (ln => {
      val label = NexusParser.extractCommandName(ln)
      val data  = NexusParser.extractCommandArgs(ln)
      NexusCharacters(label, data) })

    NexusMatrix(nexusCharacters)
  }


  def matrix: NexusMatrix = {
    if (blockNames.map(_.toLowerCase).contains("data")) {
      matrix("data")
    } else {
      matrix("characters")
    }
  }
}

/** Utility object to create [[NexusData]] from a String in NEXUS format.*/
object NexusData {

  /** Create a [[NexusData]] instance.
  *
  * @param nexusSrc A String in NEXUS format.
  */
  def apply(nexusSrc: String): NexusData = {
    val parser = NexusParser(nexusSrc)
    NexusData(parser.nexusBlocks)
  }
}
