package ee.ui.javafx.nativeImplementation
import ee.ui.primitives.Image
import javafx.scene.image.{ Image => JavaFxImage }
import ee.ui.primitives.Color
import javafx.scene.paint.{Color => JavaFxColor}
import ee.ui.primitives.Camera
import com.sun.javafx.geom.CameraImpl
import ee.ui.primitives.PerspectiveCamera
import ee.ui.javafx.application.Toolkit
import ee.ui.primitives.ParallelCamera
import javafx.scene.input.{MouseEvent => JavaFxMouseEvent}
import ee.ui.events.MouseEvent
import javafx.scene.input.{MouseButton => JavaFxMouseButton}
import ee.ui.events.MouseButton

object Converters extends Toolkit {
  def convertImage(image: Image): JavaFxImage = new JavaFxImage(image.url)
  
  def convertColor(color:Color):JavaFxColor = {
    val c = color.value
    val r = c >>> 24
    val g = c >>> 16 & 0xFF
    val b = c >>> 8 & 0xFF
    val a = c & 0xFF
    JavaFxColor.rgb(r.toInt, g.toInt, b.toInt, a / 255d)
  }
  
  def convertCamera(camera:Camera):CameraImpl = camera match {
    case c:PerspectiveCamera => toolkit createPerspectiveCamera
    case ParallelCamera => toolkit createParallelCamera
  }
  
  def convertMouseButton(m:JavaFxMouseButton):MouseButton = m match {
    case JavaFxMouseButton.MIDDLE => MouseButton.MIDDLE
    case JavaFxMouseButton.NONE => MouseButton.NONE
    case JavaFxMouseButton.PRIMARY => MouseButton.PRIMARY
    case JavaFxMouseButton.SECONDARY => MouseButton.SECONDARY
  }
  
  def convertMouseEvent(m:JavaFxMouseEvent):MouseEvent = 
    MouseEvent(
        convertMouseButton(m getButton),
        m getClickCount,
        m getSceneX,
        m getSceneY,
        m getScreenX,
        m getScreenY,
        m getX,
        m getY,
        m isAltDown,
        m isControlDown,
        m isMetaDown,
        m isMiddleButtonDown,
        m isPrimaryButtonDown,
        m isSecondaryButtonDown,
        m isShiftDown,
        m isShortcutDown,
        m isSynthesized 
    )
}