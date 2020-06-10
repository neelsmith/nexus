package edu.holycross.shot.nexus

/** One labelled row of Characters.
*
* @param label Label for row.
* @param characters Row of characters.
*/
case class NexusCharacters(label: String, characters: String) {

  /** Format delimited-text representation.
  *
  * @param separator String value to separate label and characters.
  */
  def delimited(separator: String = "#"): String = {
    s"${label}${separator}${characters}"
  }
}