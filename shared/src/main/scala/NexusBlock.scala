package edu.holycross.shot.nexus

case class NexusBlock(label: String, commands: Vector[NexusCommand]) {

  def commandNames: Vector[String] = {
    commands.map(_.commandName)
  }
}
