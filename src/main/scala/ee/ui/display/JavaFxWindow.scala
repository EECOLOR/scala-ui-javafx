package ee.ui.display

import com.sun.javafx.tk.Toolkit
import com.sun.javafx.tk.TKStage

case class JavaFxWindow(window:Window) {
  lazy val internalWindow:TKStage = Toolkit.getToolkit().createTKStage(null)
  
  def show():Unit = {
    internalWindow setVisible true
  }

  def hide():Unit = {}
}