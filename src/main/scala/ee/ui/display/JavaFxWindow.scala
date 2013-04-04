package ee.ui.display

import com.sun.javafx.tk.TKStage
import ee.ui.implementation.Toolkit
import javafx.stage.StageStyle
import javafx.stage.Modality

case class JavaFxWindow(window:Window) extends Toolkit {
  lazy val internalWindow:TKStage = {
    val internalWindow = toolkit.createTKStage(StageStyle.DECORATED, true, Modality.NONE, null)
    
    window.title foreach internalWindow.setTitle
    
    internalWindow
  }
  
  def show():Unit = {
    internalWindow setVisible true
  }

  def hide():Unit = {
    internalWindow setVisible false
  }
}