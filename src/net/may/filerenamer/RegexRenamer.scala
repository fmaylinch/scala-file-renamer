package net.may.filerenamer

/**
 * Renames using a regular expression
 */
class RegexRenamer(val regex: String, val replacement: String) extends PathPartRenamer {

  val r = regex.r

  override def renamePathPart(pathPart: String) = r.replaceAllIn(pathPart, replacement)
}