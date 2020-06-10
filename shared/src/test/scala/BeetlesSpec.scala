package edu.holycross.shot.nexus

import org.scalatest.FlatSpec
//import org.scalatest.Assertions._

class BeetlesSpec extends FlatSpec {

val charBlock = """#NEXUS

BEGIN CHARACTERS;
	TITLE  'Matrix in file "CavebeetleCOInewallv11.nex"';
	DIMENSIONS  NCHAR=848;
	FORMAT DATATYPE = DNA INTERLEAVE GAP = - MISSING = ?;
	MATRIX
	240C                      ??????????????????????????????????????????????????


  ;




END;
"""


  "A Nexus representation of beetles data" should "correctly extract command names" in {
    val nexus = Nexus(charBlock)
    val expected = Vector("TITLE","DIMENSIONS","FORMAT","MATRIX")
    assert(nexus.commandNames("CHARACTERS") == expected)

  }
}
