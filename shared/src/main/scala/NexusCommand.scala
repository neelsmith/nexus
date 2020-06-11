package edu.holycross.shot.nexus

case class NexusCommand(commandName: String, argsString: String) {

  def nexusString: String = "\t" + toString

  override def toString : String = {
    commandName + " " + argsString + ";"
  }
}
