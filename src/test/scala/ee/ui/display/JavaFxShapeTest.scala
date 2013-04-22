package ee.ui.display

import org.specs2.mutable.Specification
import com.sun.javafx.sg.PGShape
import utils.SubtypeTest
import utils.TestUtils
import scala.tools.reflect.ToolBoxError
import utils.SignatureTest
import ee.ui.display.implementation.contracts.ShapeContract
import ee.ui.display.shapes.Rectangle
import ee.ui.implementation.StubToolkit
import org.specs2.mock.Mockito
import ee.ui.display.primitives.Color
import javafx.scene.paint.{ Color => JavaFxColor }
import javafx.scene.shape.StrokeType
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import ee.ui.members.ReadOnlyEvent
import ee.ui.system.RestrictedAccess

class JavaFxShapeTest extends Specification with StubToolkit with Mockito {

  xonly
  isolated

  "JavaFxShape" should {

    val shape = new Rectangle
    val node = toolkit.createPGRectangle()
    val javaFxSchape = new JavaFxShape(shape: ShapeContract, node) {}

    "extend JavaFxNode" in {
      SubtypeTest[JavaFxShape <:< JavaFxNode]
    }

    "be abstract" in {
      TestUtils.eval("new ee.ui.display.JavaFxShape") must throwA[ToolBoxError].like {
        case e => e.getMessage must contain("class JavaFxShape is abstract")
      }
    }

    "have an internalNode property" in {
      SignatureTest[JavaFxShape, PGShape](_.internalNode)
    }

    "should fire dirty when the related properties change" in {
      var changesFired = 0

      javaFxSchape.dirty = false
      javaFxSchape.dirty.change { dirty =>
        if (dirty) {
          changesFired += 1
          javaFxSchape.dirty = false
        }
      }

      ReadOnlyEvent.fire(shape.fill.change, Some(Color.BLACK))(RestrictedAccess)
      ReadOnlyEvent.fire(shape.stroke.change, Some(Color.BLACK))(RestrictedAccess)

      changesFired === 2
    }
    
    "handle the fill property" in {
      there was one(node).setFillPaint(null)
      there was one(node).setMode(PGShape.Mode.EMPTY)
      shape.fill = Color.BLACK
      there was one(node).setFillPaint(toolkit.getPaint(JavaFxColor.rgb(0, 0, 0, 1)))
      there was one(node).setMode(PGShape.Mode.FILL)
    }

    "handle the stroke property" in {
      there was no(node).setDrawStroke(1, PGShape.StrokeType.CENTERED, PGShape.StrokeLineCap.SQUARE, PGShape.StrokeLineJoin.MITER, 10, Array.empty[Float], 0)
      there was one(node).setMode(PGShape.Mode.EMPTY)
      there was one(node).setDrawPaint(null)
      shape.stroke = Color.BLACK
      there was one(node).setDrawStroke(1, PGShape.StrokeType.CENTERED, PGShape.StrokeLineCap.SQUARE, PGShape.StrokeLineJoin.MITER, 10, Array.empty[Float], 0)
      there was one(node).setDrawPaint(toolkit.getPaint(JavaFxColor.rgb(0, 0, 0, 1)))
      there was one(node).setMode(PGShape.Mode.STROKE)
    }

    "handle the stroke and width property" in {
      there was no(node).setDrawStroke(1, PGShape.StrokeType.CENTERED, PGShape.StrokeLineCap.SQUARE, PGShape.StrokeLineJoin.MITER, 10, Array.empty[Float], 0)
      there was one(node).setFillPaint(null)
      there was one(node).setMode(PGShape.Mode.EMPTY)
      shape.stroke = Color.BLACK
      shape.fill = Color.BLACK
      there was one(node).setDrawStroke(1, PGShape.StrokeType.CENTERED, PGShape.StrokeLineCap.SQUARE, PGShape.StrokeLineJoin.MITER, 10, Array.empty[Float], 0)
      there was one(node).setMode(PGShape.Mode.STROKE_FILL)
    }
  }
}