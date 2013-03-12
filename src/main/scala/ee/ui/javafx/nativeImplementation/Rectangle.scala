package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.application.Toolkit
import scala.language.implicitConversions
import scala.language.existentials
import ee.ui.members.Property

class Rectangle(override val implemented: ee.ui.display.shape.Rectangle) extends Shape(implemented) with Toolkit {
  val internalNode = toolkit.createPGRectangle

  @inline implicit def doubleToFloat(d: Double) = d.toFloat

  (implemented.width | implemented.height | implemented.arcWidth | implemented.arcHeight) ~> {
      case (width, height, arcWidth, arcHeight) => 
        internalNode updateRectangle (0, 0, width, height, arcWidth, arcHeight)
    }
}