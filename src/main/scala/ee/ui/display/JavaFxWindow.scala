package ee.ui.display

import com.sun.javafx.tk.FocusCause
import com.sun.javafx.tk.TKStage
import com.sun.javafx.tk.TKStageListener

import ee.ui.display.detail.ReadOnlyNode
import ee.ui.display.traits.Size
import ee.ui.implementation.ContractHandlers
import ee.ui.implementation.Toolkit
import ee.ui.implementation.contracts.SceneContract
import ee.ui.implementation.contracts.WindowContract
import ee.ui.members.Property
import javafx.stage.Modality
import javafx.stage.StageStyle

case class JavaFxWindow(contract: WindowContract)(implicit contractHandlers:ContractHandlers) extends Toolkit {

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

    val boundsBinding =
      (window.width | window.height) <==> (internalWindowState.width | internalWindowState.height)

    boundsBinding.right bindWith {
      case (width, height) =>
        internalWindow.setBounds(0f, 0f, false, false, width.toFloat, height.toFloat, -1, -1, 0, 0)
    }

    internalWindow setTKStageListener internalWindowListener

    internalWindow
  }
  
  def setScene(scene:SceneContract) = {
    val internalScene = contractHandlers.scenes(this -> scene).internalScene
    internalWindow setScene internalScene
  }

  def show(): Unit = {
    window.scene foreach setScene
    
    internalWindow setVisible true
  }

  def hide(): Unit = {
    internalWindow setVisible false
  }
}