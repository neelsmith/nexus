package edu.holycross.shot.nexus


/** A single labelled block of NEXUS commands.*/
case class NexusBlock(label: String, commands: Vector[NexusCommand]) {

  /** Find names of all commands in this block.*/
  def commandNames: Vector[String] = {
    commands.map(_.commandName)
  }
}
