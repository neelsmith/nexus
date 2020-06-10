package edu.holycross.shot.nexus

import org.scalatest.FlatSpec

class BeetlesSourceSpec extends FlatSpec {

  val f = "jvm/src/test/resources/CaveTrechineCOI.nex"


  "Nexus" should "find commands in the CHARACTERS block" in {
    val nexus = NexusSource.fileParser(f)
    val cmds = nexus.commandNames("CHARACTERS")

    val expected = Vector("TITLE", "DIMENSIONS", "FORMAT", "MATRIX")
    assert(cmds == expected)
  }


}
