package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.application.Toolkit
import com.sun.javafx.tk.TKStage
import com.sun.javafx.tk.TKStageListener
import com.sun.javafx.tk.FocusCause
import com.sun.javafx.tk.TKPulseListener
import ee.ui.members.Property
import javafx.stage.StageStyle
import javafx.stage.{ Modality => JavaFxModality }
import scala.collection.JavaConversions._
import ee.ui.display.traits.ExplicitSize
import ee.ui.display.traits.ExplicitFocus
import ee.ui.display.WindowStyle
import ee.ui.display.implementation.WindowContract
import ee.ui.display.traits.ExplicitPosition
import ee.ui.display.Modality
import language.implicitConversions
import ee.ui.display.implementation.WindowImplementationHandler
import ee.ui.members.ReadOnlyProperty

class Window(val contract: WindowContract)(implicit nativeManager:NativeManager) extends NativeImplementation with Toolkit {

  val implemented = contract.read

  updateImplementation {
    internalStageBounds.update
  }

  private def closeWindow: Unit = nativeManager close implemented

  lazy val internalStage: TKStage = createInternalStage

  /*
   * Bind the stage values (updated from the internalStageListener)
   * to the implemented window. This let's the window know about 
   * the user interactions
   */
  contract.write.x <== stage.x
  contract.write.y <== stage.y
  contract.write.width <== stage.width
  contract.write.height <== stage.height
  contract.write.focused <== stage.focused

  /*
   * This object exists to that we will not recursively
   * update the internalStage
   */
  private object stage extends ExplicitPosition with ExplicitSize with ExplicitFocus
  
  implemented.scene.valueChange collect {
    case (None, Some(n)) => initScene(n)
    case (Some(o), None) => disposeScene(o)
    case (Some(o), Some(n)) => replaceScene(o, n)
    case _ => // should not happen
  }

  def initScene(scene: ee.ui.display.Scene) = {
    val internalScene = internalStage createTKScene scene.depthBuffer
    nativeManager(scene) initInternalScene internalScene
    internalStage setScene internalScene
  }

  def disposeScene(scene: ee.ui.display.Scene) =
    nativeManager(scene).disposeInternalScene

  def replaceScene(oldScene: ee.ui.display.Scene, newScene: ee.ui.display.Scene) = {
    disposeScene(oldScene)
    initScene(newScene)
  }

  def show() = {
    // set stage bounds before the window is shown
    internalStageBounds.update

    // Setup listener for changes coming back from internal stage
    internalStage setTKStageListener internalStageListener

    // This method must be called 
    // to make sure that the runtime knows the security
    // context of where this stage was created and
    // initialized
    internalStage.initSecurityContext

    implemented.scene foreach initScene

    internalStage setOpacity implemented.opacity.toFloat
    internalStage setVisible true

    toolkit.requestNextPulse
  }

  def hide() = {

    internalStage setVisible false

    internalStage setScene null

    implemented.scene.value foreach disposeScene

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
    x <== implemented.x filter (_ != stage.x.value)
    y <== implemented.y filter (_ != stage.y.value)
    width <== implemented.width filter (_ != stage.width.value)
    height <== implemented.height filter (_ != stage.height.value)

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

  def delayed(code: => Unit): () => Unit = () => code

  implemented.resizable ~> (internalStage setResizable _)
  implemented.fullScreen ~> (internalStage setFullScreen _)
  implemented.iconified ~> (internalStage setIconified _)
  implemented.title ~> (internalStage setTitle _.orNull)
  implemented.minWidth ~> { n =>
    internalStage setMinimumSize (n.toInt, implemented.minHeight.toInt)
  }
  implemented.minHeight ~> { n =>
    internalStage setMinimumSize (implemented.minWidth.toInt, n.toInt)
  }
  implemented.maxWidth ~> { n =>
    internalStage setMaximumSize (n.toInt, implemented.maxHeight.toInt)
  }
  implemented.maxHeight ~> { n =>
    internalStage setMaximumSize (implemented.maxWidth.toInt, n.toInt)
  }

  protected def createInternalStage = {
    val window = implemented.owner

    val ownerStage = window.value.map(nativeManager(_).internalStage).orNull

    val tkStage = toolkit createTKStage (
      implemented.style,
      implemented.primary,
      implemented.modality,
      ownerStage)
    tkStage setImportant true

    // Finish initialization
    tkStage setResizable implemented.resizable
    tkStage setFullScreen implemented.fullScreen
    tkStage setIconified implemented.iconified
    tkStage setTitle implemented.title.orNull
    tkStage setMinimumSize (implemented.minWidth.toInt, implemented.minHeight.toInt)
    tkStage setMaximumSize (implemented.maxWidth.toInt, implemented.maxHeight.toInt)

    val javaFxIcons = implemented.icons map Converters.convertImage

    tkStage setIcons javaFxIcons

    tkStage
  }

  implicit private def style(style: Property[WindowStyle]): StageStyle = style.value match {
    case WindowStyle.DECORATED => StageStyle.DECORATED
    case WindowStyle.TRANSPARENT => StageStyle.TRANSPARENT
    case WindowStyle.UNDECORATED => StageStyle.UNDECORATED
    case WindowStyle.UTILITY => StageStyle.UTILITY
  }

  implicit private def modality(modality: Property[Modality]): JavaFxModality = modality.value match {
    case Modality.APPLICATION_MODAL => JavaFxModality.APPLICATION_MODAL
    case Modality.NONE => JavaFxModality.NONE
    case Modality.WINDOW_MODAL => JavaFxModality.WINDOW_MODAL
  }
}