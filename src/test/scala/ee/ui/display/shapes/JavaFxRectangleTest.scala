package ee.ui.display.shapes

import org.specs2.mutable.Specification
import utils.SubtypeTest
import ee.ui.display.JavaFxNode
import ee.ui.implementation.StubToolkit
import org.specs2.mock.Mockito
import com.sun.javafx.sg.PGRectangle
import ee.ui.display.traits.Size
import ee.ui.display.JavaFxShape

class JavaFxRectangleTest extends Specification with StubToolkit with Mockito {

  xonly
  sequential
  isolated

  val rectangle = new Rectangle with Size
  val javaFxRectangle = new JavaFxRectangle(rectangle)

  "JavaFxRectangle" should {

    "extend JavaFxShape" in {
      SubtypeTest[JavaFxRectangle <:< JavaFxShape]
    }

    "call toolkit.createPGRectangle " resetToolkitMock {
      val javaFxRectangle = new JavaFxRectangle(new Rectangle)
      javaFxRectangle.internalNode must beAnInstanceOf[PGRectangle]
      there was one(stubToolkitMock).createPGRectangle()
    }

    "call internalRectangle.updateRectangle" in {
      there was one(javaFxRectangle.internalNode).updateRectangle(0, 0, 0, 0, 0, 0)
      rectangle.width = 100
      there was one(javaFxRectangle.internalNode).updateRectangle(0, 0, 100, 0, 0, 0)
      rectangle.height = 200
      there was one(javaFxRectangle.internalNode).updateRectangle(0, 0, 100, 200, 0, 0)
    }
  }
}