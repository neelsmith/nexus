package edu.holycross.shot.nexus

case class NexusData(blocks: Vector[NexusBlock]) {



  // return first block found with a given name
  def block(blockName: String) : Option[NexusBlock] = {
    val matchingName = blocks.filter(_.label.toLowerCase == blockName.toLowerCase)
    matchingName.size match {
      case 0 => None
      case _ => Some(matchingName.head)
    }
  }

  def dataMatrix = {
    val cmds = block("data").get.commands.filter(_.commandName.toLowerCase == "matrix").head.argsString
    cmds.split("\n").toVector
  }
}


object NexusData {

  def apply(nexusSrc: String): NexusData = {
    val parser = NexusParser(nexusSrc)
    NexusData(parser.nexusBlocks)
  }
}
