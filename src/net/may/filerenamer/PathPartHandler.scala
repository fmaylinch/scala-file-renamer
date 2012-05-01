package net.may.filerenamer

import java.io.File

/* Specifies what part of the path is going to be renamed, and also how to build the renamed file */
trait PathPartHandler {

  def getPathPartToRename(originalFile: File): String
  def buildRenamedFile(originalFile: File, renamedPathPart: String): File
}
