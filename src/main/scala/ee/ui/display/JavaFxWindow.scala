package ee.ui.display

import com.sun.javafx.tk.TKStage
import ee.ui.implementation.Toolkit
import javafx.stage.StageStyle
import javafx.stage.Modality
import ee.ui.members.Property
import com.sun.javafx.tk.TKStageListener
import com.sun.javafx.tk.FocusCause
import ee.ui.implementation.contracts.WindowContract
import ee.ui.display.traits.Size

case class JavaFxWindow(contract: WindowContract) extends Toolkit {

  val window = contract.window

  /**
   * This object represents the state of the internal window and is needed
   * to prevent recursive calls to for example 'setBounds'
   */
  object internalWindowState extends Size

  object internalWindowListener extends TKStageListener {

    def changedFocused(focussed: Boolean, cause: FocusCause): Unit = ???
    def changedFullscreen(fullscreen: Boolean): Unit = ???
    def changedIconified(iconified: Boolean): Unit = ???
    def changedLocation(x: Float, y: Float): Unit = ???
    def changedResizable(resizable: Boolean): Unit = ???
    def changedSize(width: Float, height: Float): Unit = {
      internalWindowState.width = width
      internalWindowState.height = height
    }
    def closed(): Unit = ???
    def closing(): Unit = ???
    def focusUngrab(): Unit = ???
  }

  lazy val internalWindow: TKStage = {
    val internalWindow = toolkit.createTKStage(StageStyle.DECORATED, true, Modality.NONE, null)

    window.title foreach internalWindow.setTitle

    val binding = 
      (window.width | window.height) <==> (internalWindowState.width | internalWindowState.height)
    
    binding.right bindWith {
        case (width, height) => 
          internalWindow.setBounds(0f, 0f, false, false, width.toFloat, height.toFloat, -1, -1, 0, 0)
      }

    internalWindow setTKStageListener internalWindowListener

    internalWindow
  }

  def show(): Unit = {
    internalWindow setVisible true
  }

  def hide(): Unit = {
    internalWindow setVisible false
  }
}