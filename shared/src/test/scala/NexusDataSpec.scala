package edu.holycross.shot.nexus

import org.scalatest.FlatSpec


class NexusDataSpec extends FlatSpec {

  val src = """#NEXUS
  bogus line we're ignoring in this example, since we're
  only reading the data block...

  BEGIN DATA;
          Dimensions NTax=10 NChar=60;
          Format DataType=DNA Interleave=yes Gap=- Missing=?;
          Matrix
  Cow     ATGGC ATATC CCATA CAACT AGGAT TCCAA GATGC AACAT CACCA ATCAT AGAAG AACTA
  Carp    ATGGCACACCCAACGCAACTAGGTTTCAAGGACGCGGCCATACCCGTTATAGAGGAACTT
  Chicken ATGGCCAACCACTCCCAACTAGGCTTTCAAGACGCCTCATCCCCCATCATAGAAGAGCTC
  Human   ATGGCACATGCAGCGCAAGTAGGTCTACAAGACGCTACTTCCCCTATCATAGAAGAGCTT
  Loach   ATGGCACATCCCACACAATTAGGATTCCAAGACGCGGCCTCACCCGTAATAGAAGAACTT
  Mouse   ATGGCCTACCCATTCCAACTTGGTCTACAAGACGCCACATCCCCTATTATAGAAGAGCTA
  Rat     ATGGCTTACCCATTTCAACTTGGCTTACAAGACGCTACATCACCTATCATAGAAGAACTT
  Seal    ATGGCATACCCCCTACAAATAGGCCTACAAGATGCAACCTCTCCCATTATAGAGGAGTTA
  Whale   ATGGCATATCCATTCCAACTAGGTTTCCAAGATGCAGCATCACCCATCATAGAAGAGCTC
  Frog    ATGGCACACCCATCACAATTAGGTTTTCAAGACGCAGCCTCTCCAATTATAGAAGAATTA
  ;
  END;

  more stuff continuing on but outside data block..."""
  val nexusData = NexusData(src)


  "A NexusData" should "have a list of blocks" in {
    val expectedBlocks = 1
    assert (nexusData.blocks.size == expectedBlocks)
  }

  it should "have non-empty lists of commands in each block" in {
    for (b <- nexusData.blocks) {
      assert(b.commands.nonEmpty)
    }
  }

  it should "extract a DATA block " in pending /*{
    val nexusSrc = """#NEXUS
    bogus line we're ignoring in this example, since we're
    only reading the data block...

    BEGIN DATA;
            Dimensions NTax=10 NChar=60;
            Format DataType=DNA Interleave=yes Gap=- Missing=?;
            Matrix
    Cow     ATGGC ATATC CCATA CAACT AGGAT TCCAA GATGC AACAT CACCA ATCAT AGAAG AACTA
    Carp    ATGGCACACCCAACGCAACTAGGTTTCAAGGACGCGGCCATACCCGTTATAGAGGAACTT
    Chicken ATGGCCAACCACTCCCAACTAGGCTTTCAAGACGCCTCATCCCCCATCATAGAAGAGCTC
    Human   ATGGCACATGCAGCGCAAGTAGGTCTACAAGACGCTACTTCCCCTATCATAGAAGAGCTT
    Loach   ATGGCACATCCCACACAATTAGGATTCCAAGACGCGGCCTCACCCGTAATAGAAGAACTT
    Mouse   ATGGCCTACCCATTCCAACTTGGTCTACAAGACGCCACATCCCCTATTATAGAAGAGCTA
    Rat     ATGGCTTACCCATTTCAACTTGGCTTACAAGACGCTACATCACCTATCATAGAAGAACTT
    Seal    ATGGCATACCCCCTACAAATAGGCCTACAAGATGCAACCTCTCCCATTATAGAGGAGTTA
    Whale   ATGGCATATCCATTCCAACTAGGTTTCCAAGATGCAGCATCACCCATCATAGAAGAGCTC
    Frog    ATGGCACACCCATCACAATTAGGTTTTCAAGACGCAGCCTCTCCAATTATAGAAGAATTA
    ;
    END;

    more stuff continuing on but outside data block..."""

    val nexus = NexusParser(nexusSrc)
    val expectedLines = 14
    val dblock = nexus.dataBlock
    assert(dblock.split("\n").size == expectedLines)
    val expectedFirst = "Dimensions NTax=10 NChar=60;"
    assert(dblock.split("\n").head.trim == expectedFirst)
    assert(dblock.split("\n").last.trim == ";")
  }*/
  it should "extract DATA lines" in pending /*{
    val nexusSrc = """#NEXUS
    bogus line we're ignoring in this example, since we're
    only reading the data block...

    BEGIN DATA;
            Dimensions NTax=10 NChar=60;
            Format DataType=DNA Interleave=yes Gap=- Missing=?;
            Matrix
    Cow     ATGGC ATATC CCATA CAACT AGGAT TCCAA GATGC AACAT CACCA ATCAT AGAAG AACTA
    Carp    ATGGCACACCCAACGCAACTAGGTTTCAAGGACGCGGCCATACCCGTTATAGAGGAACTT
    Chicken ATGGCCAACCACTCCCAACTAGGCTTTCAAGACGCCTCATCCCCCATCATAGAAGAGCTC
    Human   ATGGCACATGCAGCGCAAGTAGGTCTACAAGACGCTACTTCCCCTATCATAGAAGAGCTT
    Loach   ATGGCACATCCCACACAATTAGGATTCCAAGACGCGGCCTCACCCGTAATAGAAGAACTT
    Mouse   ATGGCCTACCCATTCCAACTTGGTCTACAAGACGCCACATCCCCTATTATAGAAGAGCTA
    Rat     ATGGCTTACCCATTTCAACTTGGCTTACAAGACGCTACATCACCTATCATAGAAGAACTT
    Seal    ATGGCATACCCCCTACAAATAGGCCTACAAGATGCAACCTCTCCCATTATAGAGGAGTTA
    Whale   ATGGCATATCCATTCCAACTAGGTTTCCAAGATGCAGCATCACCCATCATAGAAGAGCTC
    Frog    ATGGCACACCCATCACAATTAGGTTTTCAAGACGCAGCCTCTCCAATTATAGAAGAATTA
    ;
    END;

    more stuff continuing on but outside data block..."""

    val nexus = NexusParser(nexusSrc)
    val expectedLines = 14
    assert(nexus.dataLines.size == expectedLines)
    val expectedFirst = "Dimensions NTax=10 NChar=60;"
    assert(nexus.dataLines.head.trim == expectedFirst)
    assert(nexus.dataLines.last.trim == ";")
  }*/

}
