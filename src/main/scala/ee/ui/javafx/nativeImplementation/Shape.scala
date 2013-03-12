package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.Node
import com.sun.javafx.sg.PGShape
import com.sun.javafx.sg.PGShape.{ StrokeType => PGStrokeType }
import com.sun.javafx.sg.PGShape.{ StrokeLineCap => PGStrokeLineCap }
import com.sun.javafx.sg.PGShape.{ StrokeLineJoin => PGStrokeLineJoin }
import ee.ui.properties.Property
import ee.ui.display.StrokeLineJoin
import ee.ui.display.StrokeLineCap
import ee.ui.display.StrokeType
import scala.language.implicitConversions

abstract class Shape(override val implemented: ee.ui.display.Shape) extends Node(implemented) {

  val internalNode: PGShape

  val mode = new Property[PGShape.Mode](PGShape.Mode.FILL)

  mode <== (implemented.fill | implemented.stroke) map {
    case (fill, stroke) =>
      (fill.isDefined, stroke.isDefined) match {
        case (true, true) => PGShape.Mode.STROKE_FILL
        case (true, false) => PGShape.Mode.FILL
        case (false, true) => PGShape.Mode.STROKE
        case (false, false) => PGShape.Mode.EMPTY
      }
  }

  (implemented.strokeWidth |
    implemented.strokeType |
    implemented.strokeLineCap |
    implemented.strokeLineJoin |
    implemented.stroke) ~> {
      case (strokeWidth, strokeType, strokeLineCap, strokeLineJoin, stroke) =>

        if (stroke.isDefined) {
          internalNode.setDrawStroke(
            strokeWidth,
            strokeType,
            strokeLineCap,
            strokeLineJoin,
            //TODO implement these: strokeMiterLimit, dashArray and strokeDashOffset
            10.0f,
            new Array[Float](0),
            0)
        }

        internalNode setDrawPaint stroke.map(Converters.convertPaint).orNull
    }
  implemented.fill ~> (internalNode setFillPaint _.map(Converters.convertPaint).orNull)
  mode ~> (internalNode setMode _)
  implemented.antialiased ~> (internalNode setAntialiased _)

  implicit def strokeTypeToPGStrokeType(strokeType: StrokeType): PGStrokeType =
    strokeType match {
      case StrokeType.INSIDE => PGStrokeType.INSIDE
      case StrokeType.OUTSIDE => PGStrokeType.OUTSIDE
      case StrokeType.CENTERED => PGStrokeType.CENTERED
    }

  implicit def strokeLineCapToPGStrokeLineCap(strokeLineCap: StrokeLineCap): PGStrokeLineCap =
    strokeLineCap match {
      case StrokeLineCap.SQUARE => PGStrokeLineCap.SQUARE
      case StrokeLineCap.BUTT => PGStrokeLineCap.BUTT
      case StrokeLineCap.ROUND => PGStrokeLineCap.ROUND
    }

  implicit def strokeLineJoinToPGStrokeLineJoin(strokeLineJoin: StrokeLineJoin): PGStrokeLineJoin =
    strokeLineJoin match {
      case StrokeLineJoin.MITER => PGStrokeLineJoin.MITER
      case StrokeLineJoin.BEVEL => PGStrokeLineJoin.BEVEL
      case StrokeLineJoin.ROUND => PGStrokeLineJoin.ROUND
    }
}