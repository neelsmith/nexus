package edu.holycross.shot.nexus


/** A single labelled block of NEXUS commands.*/
case class NexusBlock(label: String, commands: Vector[NexusCommand]) {

  /** Find names of all commands in this block.*/
  def commandNames: Vector[String] = {
    commands.map(_.commandName)
  }

  /** Extract arguments for all instances of a command name.
  *
  * @param commandName Name of command to select.
  */
  def argsFor(commandName: String) : Vector[String]= {
    commands.filter(_.commandName == commandName).map(_.argsString)
  }


  /**
  */
  def nexusString: String = {
    (s"BEGIN ${label};" +: commands.map(_.nexusString) :+ ";").mkString("\n")
  }

  /** Override default impelmentation.*/
  override def toString: String = {
    label + " [commands: " + commandNames.distinct.mkString(", ") + "]"
  }
}
