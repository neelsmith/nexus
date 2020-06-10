package edu.holycross.shot.nexus

import wvlet.log._
// JVM only:
import wvlet.log.LogFormatter.SourceCodeLogFormatter


import scala.io.Source


object NexusSource extends LogSupport {

  def fromFile(fName: String) : NexusParser = {
    val nexusData = Source.fromFile(fName).mkString("")
    NexusParser(nexusData)
  }

}
