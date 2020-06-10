package edu.holycross.shot.nexus

import org.scalatest.FlatSpec


class NexusBlockSpec extends FlatSpec {

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
  val block = NexusData(src).blocks.head


  "A NexusBlock" should "have a label" in {
    val expectedLabel = "DATA"
    assert (block.label == expectedLabel)
  }

  it should "have non-empty lists of commands in each block" in {
    for (cmd <- block.commands) {
      cmd match {
        case nc: NexusCommand => assert(true)
        case _ => fail("Wrong type of object: " + cmd)
      }
    }
  }

}
