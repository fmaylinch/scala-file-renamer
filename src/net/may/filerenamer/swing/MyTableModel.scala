package net.may.filerenamer.swing

import javax.swing.table.AbstractTableModel
import net.may.filerenamer.RenamingJob

class MyTableModel(val renamingJob: RenamingJob) extends AbstractTableModel {

  // How to define this enumeration and case classes ?

  object Columns extends Enumeration {
    val Number, OriginalName, NewName, Differ, Ignore = Value
  }

  case class Column(name: String, clazz: java.lang.Class[_], editable: Boolean, preferredWidth: Int)

  val columns = Array(
    Column("#", classOf[Int], false, 20),
    Column("Original", classOf[String], false, 700),
    Column("New", classOf[String], true, 700),
    Column("Differ", classOf[Boolean], false, 100),
    Column("Ignore", classOf[Boolean], true, 100))

  val dstFilenames = renamingJob.dstFiles.toArray map (_.getName)
  val ignores = Array.ofDim[Boolean](dstFilenames.size)

  override def getRowCount(): Int = {
    renamingJob.srcFiles.size
  }

  override def getColumnCount(): Int = {
    columns.size
  }

  override def getValueAt(row: Int, col: Int): Object = {

    val result = Columns(col) match {
      case Columns.Number => row
      case Columns.OriginalName => renamingJob.srcFiles(row).getName
      case Columns.NewName => dstFilenames(row)
      case Columns.Differ => renamingJob.srcFiles(row).getName != dstFilenames(row)
      case Columns.Ignore => ignores(row)
      case _ => sys.error("Column index not expected: " + col)
    }

    result.asInstanceOf[AnyRef]
  }

  override def getColumnName(col: Int) = columns(col).name

  override def getColumnClass(col: Int) = columns(col).clazz

  override def isCellEditable(row: Int, col: Int) = columns(col).editable

  override def setValueAt(value: Any, row: Int, col: Int) {

    Columns(col) match {
      case Columns.NewName =>
        dstFilenames(row) = value.asInstanceOf[String]
        fireTableCellUpdated(row, col);
        fireTableCellUpdated(row, Columns.Differ.id); //  Files may or may not differ now
      case Columns.Ignore => ignores(row) = value.asInstanceOf[Boolean]
      case _ => sys.error("Column index not editable: " + col)
    }
  }
}
