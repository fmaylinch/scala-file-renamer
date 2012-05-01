package net.may.filerenamer

import java.io.File
import org.apache.commons.io.FilenameUtils

/** Lets you rename only the name of the file (includes the extension) */
object NameHandler extends PathPartHandler {

  override def getPathPartToRename(originalFile: File) = originalFile.getName

  override def buildRenamedFile(originalFile:File, renamedPathPart:String) = {

    val path = originalFile.getPath
    new FileCaseSensitive( FilenameUtils.getFullPath(path) + renamedPathPart )
  }
}