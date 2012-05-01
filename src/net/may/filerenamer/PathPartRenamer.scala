package net.may.filerenamer

import java.io.File

/**
 * Lets you focus on renaming a part of the part and let pathPartHandler decide what part of the path is taken.
 * By default, a {@link BaseNameHandler} will be used.
 */
abstract class PathPartRenamer(var pathPartHandler: PathPartHandler = BaseNameHandler) extends FileRenamer {

  override def rename(file: File) = {
    val pathPart = pathPartHandler.getPathPartToRename(file)
    pathPartHandler.buildRenamedFile(file, renamePathPart(pathPart))
  }

  def renamePathPart(path: String): String
}
