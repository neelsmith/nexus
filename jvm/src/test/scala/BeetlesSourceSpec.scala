package edu.holycross.shot.nexus

import org.scalatest.FlatSpec

class BeetlesSourceSpec extends FlatSpec {

  val f = "jvm/src/test/resources/CaveTrechineCOI.nex"


  "The NexusSource object" should "create a Nexus from a file name" in pending /*{
    val nexus = NexusSource.fromFile(f)
    //assert(nexus.nexusString == src)
    val expectedLines = 4112 // based on wc -l
    assert(nexus.nexusString.split("\n").size == expectedLines)
  }*/

  "A Nexus" should "find commands properly" in {
    val nexus = NexusSource.fromFile(f)
    val cmds = nexus.commandNames("CHARACTERS")
    //println(cmds.mkString("\n"))
  }


}
