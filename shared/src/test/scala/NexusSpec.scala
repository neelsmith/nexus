package edu.holycross.shot.nexus

import org.scalatest.FlatSpec

class NexusSpec extends FlatSpec {

  val src = """
  """


  "A Nexus instance" should "have a full String representation" in {
    val nexus = Nexus(src)
    assert(nexus.nexusString == src)
  }

  it should "extract a named block" in pending
  it should "require a header line" in pending
  it should "require a TAXA block" in pending


  /*
  #NEXUS
begin < blockname >;
    < command > < argument > [additional argument];
    [ < another command with args >; ]
end;
[ < another block with commands > ]
  */


/*
Taxa, Characters, Unaligned, Distances, Sets, Assumptions, Codons, Trees,
and Notes
*/
}
