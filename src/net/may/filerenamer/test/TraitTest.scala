package net.may.filerenamer.test

import java.io.File
import org.apache.commons.io.{FilenameUtils, FileUtils}
import org.apache.commons.lang.StringUtils
import org.apache.tools.ant.types.selectors.FilenameSelector
import org.apache.tools.ant.DirectoryScanner
import net.may.filerenamer._

object TraitTest {


  def main(args: Array[String]) {

    val scanner = new DirectoryScanner

    val includes = Array("**")
    scanner.setIncludes(includes)
    scanner.setBasedir("C:\\x\\Download\\UnedExamsRenamer")
    scanner.setCaseSensitive(true)
    scanner.scan()

    println("Files in: " + scanner.getBasedir)
    scanner.getIncludedFiles foreach println

    val renamer = new RegexRenamer("a", "b")

    val file = new File("a:/base/another/archive.avi")

    val newFile = renamer.rename(file)

    println("Old: " + file)
    println("New: " + newFile)
  }
}

