package edu.holycross.shot.nexus

import org.scalatest.FlatSpec
//import org.scalatest.Assertions._

class NexusSpec extends FlatSpec {

  val src = """#NEXUS
  """

  "A Nexus instance" should "have a full String representation" in {
    val nexus = NexusParser(src)
    assert(nexus.nexusString == src)
  }

  it should "require a header line" in {
    val badSrc = "#noheader"
    assertThrows[IllegalArgumentException] { // Result type: Assertion
      val nxs = NexusParser(badSrc)
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
    val nexus = NexusParser(nexusString)
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
    val nexus = NexusParser(nexusString)
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
    val taxa = NexusParser(nexusString).linesForBlock("taxa")
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
    val taxa = NexusParser(nexusString).block("taxa")
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

    val nexus = NexusParser(nexusSrc)
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

    val nexus = NexusParser(nexusSrc)
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

    val nexus = NexusParser(nexusSrc)
    val expectedRows = 10
    val matrix =  nexus.commandLines("data", "matrix")
    assert(matrix.size == 1)
    assert(matrix.head.size == expectedRows)
  }

  it should "extract the text of a command" in {
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
    val nexus = NexusParser(nexusString)
    val actual = nexus.command("taxa", "taxlabels")
    val expected = """Giardia
Thermus
Deinococcus
Sulfolobus
Haobacterium"""
    assert (actual.replaceAll("[ ]+", "") == expected)
  }

  it should "extract the multiple blocks" in {
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
    val nexus = NexusParser(nexusString)
    val actual = nexus.blocks("taxa")
    val expected = """dimensions ntax=5;
taxlabels
Giardia
Thermus
Deinococcus
Sulfolobus
Haobacterium
;"""
    assert (actual.replaceAll("[ ][ ]+", "") == expected)
  }


  it should "extract commands from a given block" in {
    val nexusSrc = """#NEXUS
    BEGIN TAXA;
      TITLE Taxa;
      DIMENSIONS NTAX=176;
      TAXLABELS
        240C 257C 259C 264C 265C 280C 285C 287C 288C 289C 293C 295C 296C 297C 298C 299C 300C 304C 305C 310C 313C 314C 316C 318C 322C 326C 332C 341C 343C 344C 350C 354C 361C 365C 367C 369C 370C 372C 374C 377C 385C 404C 405C 407C 409C 410C 411C 412C 415C 416C 417C 419C 422C 426C 429C 434C 453C 455C 463C 466C 469C 472C 475C 476C 477C 479C 481C 482C 486C 487C 489C 490C 492C 495C 501C 502C 503C 505C 506C 509C 510C 512C 513C 514C 518C 520C 521C 522C 523C 526C 527C 531C 532C 538C 541C 557C 559C 565C 570C 571C 572C 573C 574C 576C 578C 580C 582C 584C 587C 588C 590C 591C 593C 594C 596C 599C 601C 604C 608C 610C 620C 622C 625C 626C 629C 632C 633Cnew 636C 640C 641C 650C 651C 652C 653C 654C 655C 656C 657C 658C 659C 660C 664C 665C 666C 669C 677C 678C 679C 682C 685C 687C 689C 736C 747C 751C 756C 758C 765C 767C 770C 771C 772C 773C 776C 778C 781C 783C AmeroduvaliusWindCv AmeroduvialusPIN20140115 AmeroduvialusPOU20140114 Blemusdiscus Paraphaenopsbreuilianus Trechoblemusmicros Trechus Trechusrubens Trechuscoloradensis
      ;END;"""
      val nexus = NexusParser(nexusSrc)
      val expectedSize = 3
      assert(nexus.commands("taxa").size == expectedSize)
  }

}
