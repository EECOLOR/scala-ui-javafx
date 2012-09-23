package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.application.Toolkit
import com.sun.javafx.tk.TKStage
import ee.ui.nativeImplementation.NativeImplementation
import com.sun.javafx.tk.TKStageListener
import com.sun.javafx.tk.FocusCause
import ee.ui.traits.Size
import ee.ui.traits.Position
import ee.ui.traits.Focus
import com.sun.javafx.tk.TKPulseListener
import ee.ui.properties.Property
import ee.ui.properties.Binding._

class Window(val implemented: ee.ui.nativeElements.Window) extends NativeImplementation with NativeManagerDependencies with Toolkit {
  def internalStage: Option[TKStage] = None

  def init = {

    implemented.showing onChangedIn {
      case (false, true) => showWindow
      case (true, false) => hideWindow
    }

    /*
     * Bind the stage values (updated from the internalStageListener)
     * to the implemented window. This let's the window know about 
     * the user interactions
     */
    implemented.x <== stage.x
    implemented.y <== stage.y
    implemented.width <== stage.width
    implemented.height <== stage.height
    implemented.focused <== stage.focused

    implemented.scene onChanged {
      case (None, Some(n)) => initScene(n)
      case (Some(o), None) => disposeScene(o)
      case (Some(o), Some(n)) => replaceScene(o, n)
      case _ => // should not happen
    }
  }
  /*
   * This object exists to that we will not recursively
   * update the internalStage
   */
  lazy val stage = new Object with Position with Size with Focus

  def initScene(scene: ee.ui.nativeElements.Scene) =
    internalStage foreach { s =>
      val internalScene = s createTKScene scene.depthBuffer
      scene.nativeImplementation initInternalScene internalScene
      s setScene internalScene
    }

  def disposeScene(scene: ee.ui.nativeElements.Scene) =
    scene.nativeImplementation disposeInternalScene

  def replaceScene(oldScene: ee.ui.nativeElements.Scene, newScene: ee.ui.nativeElements.Scene) = {
    disposeScene(oldScene)
    initScene(newScene)
  }

  private def showWindow() =
    internalStage foreach { s =>
      // Setup listener for changes coming back from internal stage
      s setTKStageListener internalStageListener

      // register the bounds as a listener so it can update the internal stage
      toolkit addStageTkPulseListener internalStageBounds

      // This method must be called 
      // to make sure that the runtime knows the security
      // context of where this stage was created and
      // initialized
      s initSecurityContext

      implemented.scene foreach initScene

      // set stage bounds before the window is shown
      internalStageBounds.applyBounds

      s setOpacity implemented.opacity.toFloat
      s setVisible true
    }

  private def hideWindow() =
    internalStage foreach { s =>

      s setVisible false

      s setScene null

      implemented.scene foreach disposeScene

      // Remove toolkit pulse listener
      toolkit removeStageTkPulseListener internalStageBounds

      // Remove listener for changes coming back from internal stage
      s setTKStageListener null

      // Notify internal stage
      s close
    }

  lazy private val internalStageBounds = new TKPulseListener {
    /*
     * Bind the properties from the implemented window to the internal 
     * stage bounds, but only if the stage does not already has that 
     * value
     */
    x <== implemented.x when (_ != stage.x.value)
    y <== implemented.y when (_ != stage.y.value)
    width <== implemented.width when (_ != stage.width.value)
    height <== implemented.height when (_ != stage.height.value)

    var pulseRequested = false

    def withListener[T](default: T): Property[T] = {
      val property = new Property(default)
      property forNewValue { n => pulse }
      property
    }

    /*
     * Special defaults
     */
    val x = withListener(Double.NaN)
    val y = withListener(Double.NaN)
    val width = withListener(-1d)
    val height = withListener(-1d)
    val contentWidth = withListener(-1d)
    val contentHeight = withListener(-1d)
    val xGravity = withListener(0f)
    val yGravity = withListener(0f)

    def applyBounds = {
      pulseRequested = false
      internalStage foreach {
        _.setBounds(
          if (x.isDefault) 0 else x.toFloat,
          if (y.isDefault) 0 else y.toFloat,
          !x.isDefault,
          !y.isDefault,
          width.toFloat,
          height.toFloat,
          contentWidth.toFloat,
          contentHeight.toFloat,
          xGravity,
          yGravity)
      }

      Seq(x, y, width, height, contentWidth, contentHeight, xGravity, yGravity) foreach {
        _.reset
      }
    }

    def pulse = applyBounds

    def requestPulse = {
      if (!pulseRequested) {
        toolkit.requestNextPulse
        pulseRequested = true
      }
    }
  }

  lazy private val internalStageListener = new TKStageListener {
    def changedLocation(x: Float, y: Float) = {
      stage.x = x
      stage.y = y
    }

    def changedSize(width: Float, height: Float) = {
      stage.width = width
      stage.height = height
    }

    def changedFocused(focused: Boolean, cause: FocusCause) = {
      stage.focused = focused
    }

    def changedIconified(iconified: Boolean) = {
      // Overridden in subclasses
    }

    def changedResizable(resizable: Boolean) = {
      // Overridden in subclasses
    }

    def changedFullscreen(fs: Boolean) = {
      // Overridden in subclasses
    }

    def closing() = {
      //Event.fireEvent(window, new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    def closed() = {
      //window.hide();
    }

    def focusUngrab() = {
      //Event.fireEvent(window, new FocusUngrabEvent());
    }
  }
}