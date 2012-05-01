package net.may.filerenamer

import org.apache.commons.io.FilenameUtils
import java.io.File
import org.apache.commons.lang.StringUtils

/** Lets you rename only the base name (i.e. the name without the extension) */
object BaseNameHandler extends PathPartHandler {

  override def getPathPartToRename(originalFile: File) = FilenameUtils.getBaseName(originalFile.getName)

  override def buildRenamedFile(originalFile: File, renamedPathPart: String) = {

    val path = originalFile.getPath
    val extension = FilenameUtils.getExtension(path)
    val fullExtension = if (StringUtils.isNotEmpty(extension)) "." + extension else ""
    new FileCaseSensitive(FilenameUtils.getFullPath(path) + renamedPathPart + fullExtension)
  }
}