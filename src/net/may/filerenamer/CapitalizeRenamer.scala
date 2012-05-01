package net.may.filerenamer

import org.apache.commons.lang.WordUtils

/**
 * Capitalizes the filename using {@link WordUtils#capitalizeFully(String, char[])}.
 */
class CapitalizeRenamer(val delimiters : Array[Char]) extends PathPartRenamer {

  /** Lets you pass the array of delimiters as an String */
  def this(delimiters : String) {
    this(delimiters.toCharArray())
  }

  override def renamePathPart(pathPart: String) = WordUtils.capitalizeFully(pathPart, delimiters)
}
