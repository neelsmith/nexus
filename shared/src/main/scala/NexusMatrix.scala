package edu.holycross.shot.nexus

case class NexusMatrix(characterLines: Vector[NexusCharacters]) {

  /** Format delimited-text representation.
  *
  * @param separator String value to separate label and characters.
  */
  def delimited(separator: String = "#") : String = {
    characterLines.map(_.delimited(separator)).mkString("\n")
  }
}
