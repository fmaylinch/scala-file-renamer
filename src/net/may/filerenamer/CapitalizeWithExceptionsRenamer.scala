package net.may.filerenamer

import org.apache.commons.lang.WordUtils

/**
 * This class is like CapitalizeRenamer but doesn't capitalize some exceptions.
 * 
 * In fact, it capitalizes everything and then replaces the exceptions (incorrectly capitalized).
 * The process has a little flaw: exceptions will be replaced even as prefixes.
 * So, if "ReOrder" is an exception, the name "reorder - reordered song" will be renamed to
 * "ReOrder - ReOrdered Song" (which maybe is what you expected! ;).
 */
class CapitalizeWithExceptionsRenamer( delimiters: Array[Char], val exceptions:Seq[String] )
    
    extends CapitalizeRenamer(delimiters) {
  
  def this( delimiters: String, exceptions:Seq[String] ) {
    this( delimiters.toCharArray(), exceptions )
  }
  
  override def renamePathPart(pathPart: String) = undoExceptionCapitalizations( super.renamePathPart(pathPart) )

  /** Replaces all (wrongly) capitalized exceptions by their corresponding (untouched) exception */
  private def undoExceptionCapitalizations(name: String) =
    (name /: exceptions) (undoExceptionCapitalization(_,_))

  /** Replaces the (wrongly) capitalized exception by the (untouched) exception */
  private def undoExceptionCapitalization(name: String, ex: String) =
    name.replaceAll(WordUtils.capitalizeFully(ex), ex)
}
