package edu.holycross.shot.nexus

import org.scalatest.FlatSpec
//import org.scalatest.Assertions._

class NexusSpecMulti extends FlatSpec {

  it should  "extract groups of lines for an arbitrary block name" in {
    val nexusString = """#nexus
    Bogus line but we're skipping anything not in taxa block anyway
    begin taxa;
      dimensions ntax=5;
      taxlabels
        Giardia
        Thermus
        Deinococcus
        Sulfolobus
        Haobacterium
      ;
    end;
    more data here but we're already done."""
    val taxa = Nexus(nexusString).linesForBlockMulti("taxa")
    assert(taxa.size == 1)

    val expectedLines = 8
    val taxaBlock = taxa.head.filter(_.nonEmpty)


    assert(taxaBlock.size == expectedLines)

    val dims = "dimensions ntax=5;"
    assert(taxaBlock.head.trim == dims)
    assert(taxaBlock.last.trim == ";")

  }
}
