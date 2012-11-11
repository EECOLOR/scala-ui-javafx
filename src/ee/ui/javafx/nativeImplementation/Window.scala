package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.application.Toolkit
import com.sun.javafx.tk.TKStage
import com.sun.javafx.tk.TKStageListener
import com.sun.javafx.tk.FocusCause
import ee.ui.traits.Size
import ee.ui.traits.Position
import ee.ui.traits.Focus
import com.sun.javafx.tk.TKPulseListener
import ee.ui.properties.Property
import ee.ui.properties.Binding._

abstract class Window(val implemented: ee.ui.nativeElements.Window) extends NativeImplementation with Toolkit {
  
  def update = internalStageBounds.update
  
  protected def createInternalStage: TKStage
  protected def closeWindow:Unit
  
  lazy val internalStage: TKStage = createInternalStage

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

  implemented.showing onChangedIn {
    case (false, true) => showWindow
    case (true, false) => hideWindow
  }  
  
  implemented.scene onChangedIn {
    case (None, Some(n)) => initScene(n)
    case (Some(o), None) => disposeScene(o)
    case (Some(o), Some(n)) => replaceScene(o, n)
    case _ => // should not happen
  }

  /*
   * This object exists to that we will not recursively
   * update the internalStage
   */
  private object stage extends Position with Size with Focus

  def initScene(scene: ee.ui.nativeElements.Scene) = {
    val internalScene = internalStage createTKScene scene.depthBuffer
    NativeManager(scene) initInternalScene internalScene
    internalStage setScene internalScene
  }

  def disposeScene(scene: ee.ui.nativeElements.Scene) =
    NativeManager(scene) disposeInternalScene

  def replaceScene(oldScene: ee.ui.nativeElements.Scene, newScene: ee.ui.nativeElements.Scene) = {
    disposeScene(oldScene)
    initScene(newScene)
  }

  private def showWindow() = {
    // Setup listener for changes coming back from internal stage
    internalStage setTKStageListener internalStageListener

    // This method must be called 
    // to make sure that the runtime knows the security
    // context of where this stage was created and
    // initialized
    internalStage.initSecurityContext

    implemented.scene foreach initScene

    // set stage bounds before the window is shown
    internalStageBounds.update

    internalStage setOpacity implemented.opacity.toFloat
    internalStage setVisible true
    
    toolkit.requestNextPulse
  }

  private def hideWindow() = {

    internalStage setVisible false

    internalStage setScene null

    implemented.scene foreach disposeScene

    // Remove listener for changes coming back from internal stage
    internalStage setTKStageListener null

    // Notify internal stage
    internalStage.close
  }

  private object internalStageBounds {
    /*
     * Special defaults
     */
    val x = new Property(Double.NaN)
    val y = new Property(Double.NaN)
    val width = new Property(-1d)
    val height = new Property(-1d)
    val contentWidth = new Property(-1d)
    val contentHeight = new Property(-1d)
    val xGravity = new Property(0f)
    val yGravity = new Property(0f)

    /*
     * Bind the properties from the implemented window to the internal 
     * stage bounds, but only if the stage does not already has that 
     * value
     */
    x <== implemented.x when (_ != stage.x.value)
    y <== implemented.y when (_ != stage.y.value)
    width <== implemented.width when (_ != stage.width.value)
    height <== implemented.height when (_ != stage.height.value)    
    
    private def applyBounds = {
      
      internalStage.setBounds(
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

      Seq(x, y, width, height, contentWidth, contentHeight, xGravity, yGravity) 
      .foreach(_.reset)
    }

    def update = applyBounds
  }

  private object internalStageListener extends TKStageListener {
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
      //println("closing")
      closeWindow
    }

    def closed() = {
    	println("closed")
      //window.hide();
    }

    def focusUngrab() = {
      //Event.fireEvent(window, new FocusUngrabEvent());
    }
  }
}