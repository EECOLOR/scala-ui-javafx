package ee.ui.display

import com.sun.javafx.sg.PGShape
import ee.ui.display.implementation.contracts.ShapeContract
import ee.ui.display.implementation.contracts.NodeContract
import ee.ui.display.primitives.Color
import ee.ui.implementation.Converter
import ee.ui.members.Property

abstract class JavaFxShape(shape: ShapeContract, override val internalNode: PGShape) extends JavaFxNode(shape.asNodeContract, internalNode) {

  val mode = Property[PGShape.Mode](PGShape.Mode.EMPTY)

  val bindToShape: Unit = {

    mode <== (shape.fill | shape.stroke) map {
      case (fill, stroke) =>
        (fill.isDefined, stroke.isDefined) match {
          case (true, true) => PGShape.Mode.STROKE_FILL
          case (true, false) => PGShape.Mode.FILL
          case (false, true) => PGShape.Mode.STROKE
          case (false, false) => PGShape.Mode.EMPTY
        }
    }
    mode bindWith internalNode.setMode

    shape.fill bindWith {
      case fill =>
        internalNode.setFillPaint(fill.map(Converter.convert).orNull)
    }

    shape.stroke bindWith {
      case stroke =>
        if (stroke.isDefined)
          internalNode.setDrawStroke(1, PGShape.StrokeType.CENTERED, PGShape.StrokeLineCap.SQUARE, PGShape.StrokeLineJoin.MITER, 10, Array.empty[Float], 0)

        internalNode.setDrawPaint(stroke.map(Converter.convert).orNull)
    }
    
    (shape.stroke.change | shape.fill.change) {
      dirty = true
    }
  }
}