package net.may.filerenamer.test

import net.may.filerenamer._

object Test {

  def main(args: Array[String]) {
    
    val exceptions = List("DT8", "ReOrder")
    
    val ren = new CapitalizeWithExceptionsRenamer(" ,".toCharArray(), exceptions);
    
    println("Delimiters: " + ren.delimiters.mkString("[","","]"));
    println("Exceptions: " + ren.exceptions);
    
    val result = ren.renamePathPart("dt8 and reorder make dt8reorder")
    
    println( result )
  }
}