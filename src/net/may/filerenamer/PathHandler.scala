package net.may.filerenamer

import java.io.File

/** Lets you rename the whole path */
object PathHandler extends PathPartHandler {

  override def getPathPartToRename(originalFile: File) = originalFile.getPath
  override def buildRenamedFile(originalFile:File, renamedPathPart:String) = new FileCaseSensitive(renamedPathPart)
}
