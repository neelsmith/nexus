package edu.holycross.shot.nexus

case class NexusMatrix(rows: Vector[NexusCharacters]) {

  /** Format delimited-text representation.
  *
  * @param separator String value to separate label and characters.
  */
  def delimited(separator: String = "#") : String = {
    rows.map(_.delimited(separator)).mkString("\n")
  }

  def labels : Vector[String] = rows.map(_.label)
  def data : Vector[String] = rows.map(_.characters)
}
