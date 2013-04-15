package ee.ui.display.shapes

import ee.ui.display.JavaFxNode
import ee.ui.display.implementation.contracts.RectangleContract
import com.sun.javafx.sg.PGRectangle
import ee.ui.implementation.Toolkit._
import ee.ui.display.JavaFxShape
import com.sun.javafx.geom.BaseBounds

case class JavaFxRectangle(rectangle: RectangleContract, override val internalNode: PGRectangle = toolkit.createPGRectangle()) extends JavaFxShape(rectangle, internalNode) {

  val bindToRectangle: Unit = {
    (rectangle.width | rectangle.height) bindWith {
      case (width, height) =>
        /*
        internalNode setTransformedBounds BaseBounds.getInstance(
          0, 0, 0,
          width.toFloat, height.toFloat, 0)
          */
        internalNode.updateRectangle(0, 0, width.toFloat, height.toFloat, 0, 0)
        dirty.fire()
    }
  }
}