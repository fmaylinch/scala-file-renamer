package net.may.filerenamer

import java.io.File

/** Renames a file */
trait FileRenamer {

  def rename(file: File): File
}