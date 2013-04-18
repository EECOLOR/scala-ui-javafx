package ee.ui.implementation

import javafx.scene.paint.{ Color => JavaFxColor }
import ee.ui.display.primitives.Color
import javafx.scene.paint.{ Paint => JavaFxPaint }
import com.sun.javafx.geom.BaseBounds
import ee.ui.primitives.Bounds

object Converter extends Toolkit {

  def convert(color: Color): AnyRef = {
    val c = color.value
    val r = c >> 16 & 0xFF
    val g = c >> 8 & 0xFF
    val b = c & 0xFF

    convert(JavaFxColor.rgb(r, g, b, color.alpha))
  }

  def convert(paint: JavaFxPaint): AnyRef = toolkit getPaint paint

  def convert(b: Bounds): BaseBounds =
    BaseBounds getInstance (
      b.minX.toFloat, b.minY.toFloat, b.minZ.toFloat,
      b.maxX.toFloat, b.maxY.toFloat, b.maxZ.toFloat)
}