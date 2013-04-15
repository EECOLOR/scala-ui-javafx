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

object JavaFxShapeTest extends Specification with StubToolkit with Mockito {

  xonly

  "JavaFxShape" should {

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
    
    "handle the fill property" in {
      val shape = new Rectangle
      val node = toolkit.createPGRectangle()
      new JavaFxShape(shape:ShapeContract, node) {}
      there was one(node).setFillPaint(null)    
      there was one(node).setMode(PGShape.Mode.EMPTY)
      shape.fill = Color.BLACK
      there was one(node).setFillPaint(toolkit.getPaint(JavaFxColor.rgb(0, 0, 0, 1)))    
      there was one(node).setMode(PGShape.Mode.FILL)
    }
  }
}