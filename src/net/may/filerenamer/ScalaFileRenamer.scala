package net.may.filerenamer

import java.io.{ File, FileFilter }
import scala.collection.mutable.ListBuffer
import scala.tools.nsc.interpreter.IMain
import scala.tools.nsc.Settings
import scala.io.Source
import java.util.Properties
import org.apache.tools.ant.DirectoryScanner

object ScalaFileRenamer {

  def main(args: Array[String]) {
    
    val config = loadProperties("config.properties")
    val scriptsDir = config.getProperty("scripts.dir")
    
    if (args.length < 3) {
      println("Arguments: DIRECTORY FILES_TO_RENAME SCRIPT_NAME [SCRIPT_ARGUMENTS]")
      println(""" Example: c:/dance "*.mp3" dance "ReOrder DT8 ATB" """)
      println(""" Example: c:/music "**/*.wav" regex "(.+) - (.+)" "$2 - $1" """)
      println(" Scripts dir: " + scriptsDir)
      println(" Available scripts: " + getScriptsNames(scriptsDir).mkString(", "))
      sys.exit()
    }
    
    // Arguments
    val dirToRename = args(0)
    val regexFilesToRename = args(1)
    val renamersScript = args(2)
    val scriptArgs = args.drop(3)

    println("Arguments:")
    println("  Directory   : " + dirToRename)
    println("  Files       : " + regexFilesToRename)
    println("  Script      : " + renamersScript)
    println("  Script args : " + scriptArgs.mkString(", "))
    
    val files = getFiles(dirToRename, regexFilesToRename)

    // Load script and interpret it
    val scriptPath = scriptsDir + "/" + renamersScript + ".scala"
    val script = Source.fromFile(scriptPath).mkString

    val renamers = getRenamersFromScript(script, scriptArgs)

    val job = new RenamingJob(files, renamers)
    
    doJob(job)
  }

  /** Returns a collection of renamers that are created in the script */
  private def getRenamersFromScript(script: String, args: Seq[String]) : Seq[FileRenamer] = {

    // Interpreter
    val main = newIMainWithClasspath

    main.beQuietDuring( main.addImports("net.may.filerenamer._") )

    // Pass mutable list to interpreter so script can add renamers to it
    val renamers : ListBuffer[FileRenamer] = ListBuffer()
    main.beQuietDuring( main.bind("renamers", renamers) )

    // Pass optional arguments to script
    main.beQuietDuring( main.bind("args", args.toList) )

    main.quietRun(script) // interpret() outputs results

    renamers
  }

  /** Creates an IMain with its classpath set to the system property "java.class.path" */
  private def newIMainWithClasspath = {

    val settings = new Settings(sys.error)
    settings.classpath.value = System.getProperty("java.class.path")

    new IMain(settings)
  }

  private def doJob (job: RenamingJob) {

    job.showCompletePaths = true

    println()
    job.printInfo()
    
    val somethingToRename = job.srcFiles != job.dstFiles

    if (somethingToRename) {

      if (ask("Rename?")) {

        println("Renaming...")
        job.rename()

        if (ask("Undo?")) {

          println("Undoing...")
          job.reverseRename()
        }
      }
    } else {
      println("Nothing to rename")
    }

    def ask(question: String) = {
      print(question + " ")
      Console.readBoolean()
    }
  }

  /** Gets a collection of File using the ant regex from the base directory */
  private def getFiles(basedir: String, antFileRegex: String) : Seq[File] = {

    val scanner = new DirectoryScanner

    scanner.setIncludes(Array(antFileRegex))
    scanner.setBasedir(basedir)
    scanner.setCaseSensitive(true)
    scanner.scan()

    scanner.getIncludedFiles map (relativePath => new FileCaseSensitive(basedir + File.separator + relativePath))
  }

  /** Loads a properties file from the classpath */
  private def loadProperties(propertiesFilename: String) = {
    
    val properties = new Properties()
    properties.load( ClassLoader.getSystemResourceAsStream(propertiesFilename) )
    properties
  }
  
  /** Returns the names of the script files without the ".scala" extension */
  private def getScriptsNames(scriptsDir: String) = {
     (getFiles(scriptsDir, "*.scala") map (_.getName.dropRight(".scala".length)))
  }
}