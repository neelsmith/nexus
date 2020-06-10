package edu.holycross.shot.nexus

import org.scalatest.FlatSpec

class NexusSourceSpec extends FlatSpec {

  val f = "jvm/src/test/resources/CaveTrechineCOI.nex"


  "The NexusSource object" should "create a NexusParser from a file name" in {
    val nexusParsers = NexusSource.fileParser(f)
    val expectedLines = 4112 // based on wc -l
    assert(nexusParsers.nexusString.split("\n").size == expectedLines)
  }

  it should "create a NexusData from a file name" in {
    val nexus = NexusSource.fromFile(f)
    val expectedBlocks = 9
    assert(nexus.blocks.size == expectedBlocks)
  }



}
