package edu.holycross.shot.nexus

import wvlet.log._
// JVM only:
import wvlet.log.LogFormatter.SourceCodeLogFormatter


import scala.io.Source


object NexusSource extends LogSupport {

  def fromFile(fName: String) : NexusData = {
    val nexusData = Source.fromFile(fName).mkString("")
    NexusData(nexusData)
  }

  def fromUrl(url: String) : NexusData = {
    val nexusData = Source.fromURL(url).mkString("")
    NexusData(nexusData)
  }

  def fileParser(fName: String) : NexusParser = {
    val nexusData = Source.fromFile(fName).mkString("")
    NexusParser(nexusData)
  }

}
