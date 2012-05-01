package net.may.filerenamer

import java.io.File

/**
 * A renaming function from a sequence of simple renamers
 */
class SequenceRenamerFunction (renamers : Seq[FileRenamer]) extends (Seq[File] => Seq[File]) {
  
  def apply(srcFiles: Seq[File]) = {
    
    for( file <- srcFiles ) yield (file /: renamers) ((file, renamer) => renamer rename file)
  }
}
