package net.may.filerenamer.swing

import scala.swing._
import java.awt.Dimension
import java.io.File
import net.may.filerenamer.RenamingJob

object ScalaSwingFileRenamer extends SimpleSwingApplication {

  override def top = new MainFrame {

    title = "File Renamer"

    val button = new Button {
      text = "Rename! (not implemented yet)"
      enabled = false
    }

    var scrollPane = new ScrollPane

    contents = new BoxPanel(Orientation.Vertical) {
      contents += scrollPane
      contents += button
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    val fileChooser = new FileChooser(new File("c:/")) {
      fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly
    }

    fileChooser.showOpenDialog(scrollPane) match {
      case FileChooser.Result.Approve => showTable(fileChooser.selectedFile, scrollPane)
    }

    pack
  }

  def showTable(folder: File, scrollPane: ScrollPane) {

    val table = new Table {

      val renamers = List() // How to configure renamers in the GUI ?

      val files = folder.listFiles

      val job = new RenamingJob(files, renamers)

      val myModel = new MyTableModel(job)

      model = myModel

      val columnModel = peer.getColumnModel

      myModel.Columns.values foreach {
        col =>
          columnModel.getColumn(col.id).setPreferredWidth(myModel.columns(col.id).preferredWidth)
      }
    }

    scrollPane.contents = table
  }
}