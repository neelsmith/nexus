package edu.holycross.shot.nexus

/** One labelled row of Characters.
*
* @param label Label for row.
* @param characters Row of characters.
*/
case class NexusCharacters(label: String, characters: String) {


  /** True if both label and characters are empty. */
  def isEmpty: Boolean = {
    label.isEmpty && characters.isEmpty
  }

  /** Format delimited-text representation.
  *
  * @param separator String value to separate label and characters.
  */
  def delimited(separator: String = "#"): String = {
    s"${label}${separator}${characters}"
  }


  override def toString: String = {
    delimited(" ")
  }
}
