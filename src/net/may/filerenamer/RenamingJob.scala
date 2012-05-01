package net.may.filerenamer

import java.io.File
import collection.Seq


/**
 * Lets you rename a sequence of files using a renaming function (primary constructor) or
 * from a sequence of FileRenamer.
 */
class RenamingJob (val srcFiles: Seq[File], val renamer: Seq[File] => Seq[File]) {

  /** Lets you rename an array of files using a sequence of simple renamers */
  def this(srcFiles: Seq[File], renamers : Seq[FileRenamer]) {
    this(srcFiles, new SequenceRenamerFunction(renamers))
  }

  /** Destination files (srcFiles after applying renamer) */
  val dstFiles = renamer(srcFiles)

  var showEqualFiles = false
  var showCompletePaths = false

  def getFileName(file: File) = if (showCompletePaths) file.getPath else file.getName

  /** Prints information about the files that would be renamed */
  def printInfo() {

    val pairs = srcFiles zip dstFiles
    val pairsToShow = if (showEqualFiles) pairs else pairs filter (pair => pair._1 != pair._2)

    for ((srcFile, dstFile) <- pairsToShow) {

      val eq = if (srcFile == dstFile) "==" else "<>"

      println("   From : " + getFileName(srcFile))
      println(eq + "   To : " + getFileName(dstFile))
    }
  }

  /** Renames each file from srcFiles to its corresponding destination file in dstFiles */
  def rename() {
    
    for( (srcFile, dstFile) <- srcFiles zip dstFiles ) {
      
      srcFile.renameTo(dstFile)
    }
  }
  
  /** Renames each file from dstFiles to its corresponding source file in srcFiles */
  def reverseRename() {
    
    for( (srcFile, dstFile) <- srcFiles zip dstFiles ) {
      
      dstFile.renameTo(srcFile)
    }
  }
}
