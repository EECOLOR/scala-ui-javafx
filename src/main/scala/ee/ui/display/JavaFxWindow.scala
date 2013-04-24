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
import scala.reflect.ClassTag
import java.security.AccessController

case class JavaFxWindow(contract: WindowContract)(implicit contractHandlers: ContractHandlers) extends Toolkit {

  val window = contract.window

  /**
   * This object represents the state of the internal window and is needed
   * to prevent recursive calls to for example 'setBounds'
   */
  object internalWindowState extends Size

  object internalWindowListener extends TKStageListener {

    def changedFocused(focussed: Boolean, cause: FocusCause): Unit = println("JavaFxWindow - Implement changedFocused")
    def changedFullscreen(fullscreen: Boolean): Unit = ???
    def changedIconified(iconified: Boolean): Unit = ???
    def changedLocation(x: Float, y: Float): Unit = println("JavaFxWindow - Implement changedLocation")
    def changedResizable(resizable: Boolean): Unit = ???
    def changedSize(width: Float, height: Float): Unit = {
      internalWindowState.width = width
      internalWindowState.height = height
    }
    def closed(): Unit = ???
    def closing(): Unit = ???
    def focusUngrab(): Unit = ???
    def changedMaximized(x$1: Boolean): Unit = ???
    def initAccessibleTKStageListener(x$1: Long): Unit = ???

  }

  val internalWindow: TKStage = toolkit.createTKStage(StageStyle.DECORATED, true, Modality.NONE, null, true)

  val bindToWindow: Unit = {

    internalWindow.setSecurityContext(AccessController.getContext)
    
    window.title foreach internalWindow.setTitle

    val boundsBinding =
      (window.width | window.height) <==> (internalWindowState.width | internalWindowState.height)

    boundsBinding.right bindWith {
      case (width, height) =>
        internalWindow.setBounds(0f, 0f, false, false, width.toFloat, height.toFloat, -1, -1, 0, 0)
    }

    internalWindow setTKStageListener internalWindowListener

    def internalScene(scene: SceneContract) =
      contractHandlers.scenes(this -> scene).internalScene

    def setScene(scene: SceneContract) = internalWindow setScene internalScene(scene)
    def removeScene(scene: SceneContract) = contractHandlers.scenes.removeContract(this -> scene)

    window.scene foreach setScene

    window.scene.valueChange {
      case (Some(oldScene), Some(newScene)) => {
        removeScene(oldScene)
        setScene(newScene)
      }
      case (None, Some(newScene)) => setScene(newScene)
      case (Some(oldScene), None) => {
        removeScene(oldScene)
        internalWindow setScene null
      }
      case (None, None) => // should not happen
    }
  }

  def show(): Unit = {
    internalWindow setVisible true
  }

  def hide(): Unit = {
    internalWindow setVisible false
  }
}