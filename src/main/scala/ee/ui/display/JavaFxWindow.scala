package ee.ui.display

import com.sun.javafx.tk.TKStage
import ee.ui.implementation.Toolkit
import javafx.stage.StageStyle
import javafx.stage.Modality
import ee.ui.members.Property

case class JavaFxWindow(window: Window) extends Toolkit {

  lazy val internalWindow: TKStage = {
    val internalWindow = toolkit.createTKStage(StageStyle.DECORATED, true, Modality.NONE, null)

    window.title foreach internalWindow.setTitle

    val bounds = Property[(Double, Double)]((-1f, -1f))
    bounds change { case (width, height) =>
        internalWindow.setBounds(0f, 0f, true, true, width.toFloat, height.toFloat, -1, -1, 0, 0)
    }

    bounds <== window.width | window.height

    internalWindow
  }

  def show(): Unit = {
    internalWindow setVisible true
  }

  def hide(): Unit = {
    internalWindow setVisible false
  }
}