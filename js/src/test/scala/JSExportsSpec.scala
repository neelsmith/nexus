package edu.holycross.shot.nexus

import org.scalatest._


class JSExportsSpec extends FlatSpec {

	"The Scala export of Nexus"  should "expose the case class and its methods" in {
		val s = "Test export"
    assert(Nexus(s).nexusString == s)
	}

}
