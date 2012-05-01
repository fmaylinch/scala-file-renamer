package net.may.filerenamer

import java.io.File

/**
 * When comparing files, takes case into account (in Windows, for example, case is not relevant)
 */
class FileCaseSensitive(pathname:String) extends File(pathname) {

  // TODO: IDEA marks an error here but it runs fine
  override def equals(obj: Any) =
    if (super.equals(obj))
      this.getPath.equals(obj.asInstanceOf[File].getPath)
    else
      false
}
