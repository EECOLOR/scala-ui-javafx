package ee.ui.display

import com.sun.javafx.sg.PGShape
import ee.ui.display.implementation.contracts.ShapeContract
import ee.ui.display.implementation.contracts.NodeContract
import ee.ui.display.primitives.Color
import ee.ui.implementation.Converter

abstract class JavaFxShape(shape: ShapeContract, override val internalNode: PGShape) extends JavaFxNode(shape.asNodeContract, internalNode) {

  val bindToShape: Unit = {
    def getMode(fill: Option[Color]) =
      fill.isDefined match {
        case true => PGShape.Mode.FILL
        case false => PGShape.Mode.EMPTY
      }

    shape.fill bindWith {
      case fill =>
        internalNode.setMode(getMode(fill))
        internalNode.setFillPaint(fill.map(Converter.convert).orNull)
    }
  }
}