package ee.ui.javafx.nativeImplementation
import ee.ui.primitives.Image
import javafx.scene.image.{ Image => JavaFxImage }
import ee.ui.primitives.Color
import javafx.scene.paint.{ Color => JavaFxColor }
import ee.ui.primitives.Camera
import com.sun.javafx.geom.CameraImpl
import ee.ui.primitives.PerspectiveCamera
import ee.ui.javafx.application.Toolkit
import ee.ui.primitives.ParallelCamera
import javafx.scene.input.{ MouseEvent => JavaFxMouseEvent }
import ee.ui.events.MouseEvent
import javafx.scene.input.{ MouseButton => JavaFxMouseButton }
import ee.ui.events.MouseButton
import ee.ui.primitives.Paint
import ee.ui.primitives.Font
import javafx.scene.text.{ Font => JavaFxFont }
import com.sun.prism.paint.{ Paint => JavaFxPaint }
import ee.ui.primitives.Bounds
import com.sun.javafx.geom.BaseBounds
import ee.ui.events.KeyEvent
import javafx.scene.input.{ KeyEvent => JavaFxKeyEvent }
import ee.ui.events.CharacterTypedEvent
import ee.ui.primitives.KeyCode
import javafx.scene.input.{ KeyCode => JavaFxKeyCode }
import ee.ui.application.DataFormat
import javafx.scene.input.{ DataFormat => JavaFxDataFormat }
import ee.ui.primitives.FontMetrics
import com.sun.javafx.tk.{ FontMetrics => JavaFxFontMetrics }

object Converters extends Toolkit {
  def convertImage(image: Image): JavaFxImage = new JavaFxImage(image.url)

  def convertPaint(paint: Paint): AnyRef = paint match {
    case color: Color => toolkit getPaint convertColor(color)
  }

  def convertColor(color: Color): JavaFxColor = {
    val c = color.value
    val r = c >> 16 & 0xFF
    val g = c >> 8 & 0xFF
    val b = c & 0xFF

    JavaFxColor.rgb(r, g, b, color.alpha)
  }

  def convertCamera(camera: Camera): CameraImpl = camera match {
    case c: PerspectiveCamera => toolkit createPerspectiveCamera
    case ParallelCamera => toolkit createParallelCamera
  }

  def convertMouseButton(m: JavaFxMouseButton): MouseButton = m match {
    case JavaFxMouseButton.MIDDLE => MouseButton.MIDDLE
    case JavaFxMouseButton.NONE => MouseButton.NONE
    case JavaFxMouseButton.PRIMARY => MouseButton.PRIMARY
    case JavaFxMouseButton.SECONDARY => MouseButton.SECONDARY
  }

  def convertMouseEvent(m: JavaFxMouseEvent): MouseEvent =
    MouseEvent(
      convertMouseButton(m.getButton),
      m.getClickCount,
      m.getSceneX,
      m.getSceneY,
      m.getScreenX,
      m.getScreenY,
      m.isAltDown,
      m.isControlDown,
      m.isMetaDown,
      m.isMiddleButtonDown,
      m.isPrimaryButtonDown,
      m.isSecondaryButtonDown,
      m.isShiftDown,
      m.isShortcutDown,
      m.isSynthesized)

  def convertKeyEvent(k: JavaFxKeyEvent): KeyEvent =
    KeyEvent(
      convertKeyCode(k.getCode),
      k.getText,
      k.isShiftDown,
      k.isControlDown,
      k.isAltDown,
      k.isMetaDown)

  def convertKeyCode(keyCode: JavaFxKeyCode): KeyCode = KeyCode from keyCode.impl_getCode

  def convertCharacterTypedEvent(k: JavaFxKeyEvent): CharacterTypedEvent =
    CharacterTypedEvent(
      k.getCharacter,
      k.isShiftDown,
      k.isControlDown,
      k.isAltDown,
      k.isMetaDown)

  def convertFont(f: Font): JavaFxFont = new JavaFxFont(f.name, f.size.getOrElse(-1))
  def convertFont(f: JavaFxFont): Font = {
    val size = f.getSize

    Font(f.getName, if (size == -1) None else Some(size))
  }

  def convertBounds(b: Bounds): BaseBounds =
    BaseBounds getInstance (
      b.minX.toFloat, b.minY.toFloat, b.minZ.toFloat,
      b.maxX.toFloat, b.maxY.toFloat, b.maxZ.toFloat)

  def convertDataFormat(d: DataFormat): JavaFxDataFormat =
    new JavaFxDataFormat(d.ids: _*)

  def convertFontMetrics(f: JavaFxFontMetrics): FontMetrics =
    FontMetrics(
      f.getMaxAscent,
      f.getAscent,
      f.getXheight,
      f.getDescent,
      f.getMaxDescent,
      f.getLeading,
      convertFont(f.getFont))
}