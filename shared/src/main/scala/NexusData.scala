package edu.holycross.shot.nexus

case class NexusData(blocks: Vector[NexusBlock])


object NexusData {

  def apply(nexusSrc: String): NexusData = {
    val parser = NexusParser(nexusSrc)
    NexusData(parser.nexusBlocks)
  }
}
