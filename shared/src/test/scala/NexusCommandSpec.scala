package edu.holycross.shot.nexus

import org.scalatest.FlatSpec


class NexusCommandSpec extends FlatSpec {

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
  val commands = NexusData(src).blocks.head.commands


  "A NexusCommand" should "have a name" in {
    val expectedNames = Vector("Dimensions", "Format", "Matrix")
    assert (commands.map(_.commandName) == expectedNames)
  }

  it should "have an args string" in {
    val args = commands.map(_.argsString)
    val expectedFirst = "NTax=10 NChar=60"
    assert(args.head == expectedFirst)
    val matrixLines = args.last.split("\n")
    val expectedLines = 10
    assert(matrixLines.size == expectedLines)
  }

}
