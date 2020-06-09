package edu.holycross.shot.nexus

import org.scalatest.FlatSpec
//import org.scalatest.Assertions._

class NexusSpec extends FlatSpec {

  val src = """#NEXUS
  """


  "A Nexus instance" should "have a full String representation" in {
    val nexus = Nexus(src)
    assert(nexus.nexusString == src)
  }

  it should "require a header line" in {
    val badSrc = "#noheader"
    assertThrows[IllegalArgumentException] { // Result type: Assertion
      val nxs = Nexus(badSrc)
    }
  }
  it should  "extract an artibtrarily named block" in pending
  it should  "extract lines for an arbitrarily named block" in pending
  it should "extract DATA lines" in pending
  it should "extract a DATA block" in pending


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
