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

  it should "find all block names" in {
    val nexusString = """#nexus
    BEGIN TAXA;
    TaxLabels Scarabaeus Drosophila Aranaeus;
    END;

    BEGIN TREES;
      Translate beetle Scarabaeus, fly Drosophila, spider Aranaeus;
      Tree tree1 = ((1,2),3);
      Tree tree2 = ((beetle,fly),spider);
      Tree tree3 = ((Scarabaeus,Drosophila),Aranaeus);
    END;"""
    val nexus = Nexus(nexusString)
    val expectedBlocks = Vector("TAXA", "TREES")
    assert(nexus.blockNames == expectedBlocks)
  }


  it should "find command names within a block" in {
    val nexusString = """#nexus
    BEGIN TAXA;
    TaxLabels Scarabaeus Drosophila Aranaeus;
    END;

    BEGIN TREES;
      Translate beetle Scarabaeus, fly Drosophila, spider Aranaeus;
      Tree tree1 = ((1,2),3);
      Tree tree2 = ((beetle,fly),spider);
      Tree tree3 = ((Scarabaeus,Drosophila),Aranaeus);
    END;"""
    val nexus = Nexus(nexusString)
    val expectedCommands = Vector("Translate", "Tree")
    assert(nexus.commandNames("TREES") == expectedCommands)
  }


  it should  "extract lines for an arbitrarily named block" in {
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
    val taxa = Nexus(nexusString).linesForBlock("taxa")
    val expectedLines = 8
    assert(taxa.size == expectedLines)
    val dims = "dimensions ntax=5;"
    assert(taxa.head.trim == dims)
    assert(taxa.last.trim == ";")
  }


  it should  "extract an arbitrarily named block" in {
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
    val expectedWithoutSpaces = "dimensionsntax=5;\ntaxlabels\nGiardia\nThermus\nDeinococcus\nSulfolobus\nHaobacterium\n;"
    val taxa = Nexus(nexusString).block("taxa")
    assert(taxa.replaceAll("[ ]+", "") == expectedWithoutSpaces)
  }

  it should "support omitting comments" in pending



  it should "extract a DATA block" in {
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

    val nexus = Nexus(nexusSrc)
    val expectedLines = 14
    val dblock = nexus.dataBlock
    assert(dblock.split("\n").size == expectedLines)
    val expectedFirst = "Dimensions NTax=10 NChar=60;"
    assert(dblock.split("\n").head.trim == expectedFirst)
    assert(dblock.split("\n").last.trim == ";")
  }
  it should "extract DATA lines" in {
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

    val nexus = Nexus(nexusSrc)
    val expectedLines = 14
    assert(nexus.dataLines.size == expectedLines)
    val expectedFirst = "Dimensions NTax=10 NChar=60;"
    assert(nexus.dataLines.head.trim == expectedFirst)
    assert(nexus.dataLines.last.trim == ";")
  }


  it should "extract a command from a block" in {
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

    val nexus = Nexus(nexusSrc)
    val expectedRows = 10
    val matrix =  nexus.commandLines("data", "matrix")
    assert(matrix.size == expectedRows)
  }



}
