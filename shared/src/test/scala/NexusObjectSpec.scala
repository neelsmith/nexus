package edu.holycross.shot.nexus

import org.scalatest.FlatSpec
//import org.scalatest.Assertions._

class NexusObjectSpec extends FlatSpec {


  val nexusSrc = """#NEXUS
  BEGIN TAXA;
  	TITLE Taxa;
  	DIMENSIONS NTAX=176;
  	TAXLABELS
  		240C 257C 259C 264C 265C 280C 285C 287C 288C 289C 293C 295C 296C 297C 298C 299C 300C 304C 305C 310C 313C 314C 316C 318C 322C 326C 332C 341C 343C 344C 350C 354C 361C 365C 367C 369C 370C 372C 374C 377C 385C 404C 405C 407C 409C 410C 411C 412C 415C 416C 417C 419C 422C 426C 429C 434C 453C 455C 463C 466C 469C 472C 475C 476C 477C 479C 481C 482C 486C 487C 489C 490C 492C 495C 501C 502C 503C 505C 506C 509C 510C 512C 513C 514C 518C 520C 521C 522C 523C 526C 527C 531C 532C 538C 541C 557C 559C 565C 570C 571C 572C 573C 574C 576C 578C 580C 582C 584C 587C 588C 590C 591C 593C 594C 596C 599C 601C 604C 608C 610C 620C 622C 625C 626C 629C 632C 633Cnew 636C 640C 641C 650C 651C 652C 653C 654C 655C 656C 657C 658C 659C 660C 664C 665C 666C 669C 677C 678C 679C 682C 685C 687C 689C 736C 747C 751C 756C 758C 765C 767C 770C 771C 772C 773C 776C 778C 781C 783C AmeroduvaliusWindCv AmeroduvialusPIN20140115 AmeroduvialusPOU20140114 Blemusdiscus Paraphaenopsbreuilianus Trechoblemusmicros Trechus Trechusrubens Trechuscoloradensis
  	;END;"""
    val nexus = NexusParser(nexusSrc)


  "The Nexus object" should "extract lines identified by opening/closing patterns" in {
    val extract = NexusParser.extractLines(nexus.lines, "BEGIN taxa;", "END;")
    // expect 5 lines contained with a single block:
    assert(extract.size == 1)
    val expectedLines = 5
    assert(extract.head.size == expectedLines)
  }

  it should "find NEXUS command name within a command line" in {

    val cnames = nexus.commandNames("taxa")
    println(cnames)

  }

}
