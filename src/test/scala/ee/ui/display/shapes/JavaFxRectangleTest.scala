package ee.ui.display.shapes

import org.specs2.mutable.Specification
import utils.SubtypeTest
import ee.ui.display.JavaFxNode
import ee.ui.implementation.StubToolkit
import org.specs2.mock.Mockito
import com.sun.javafx.sg.PGRectangle

object JavaFxRectangleTest extends Specification with StubToolkit with Mockito {
  
  xonly
  
  val javaFxRectangle = new JavaFxRectangle(new Rectangle)
  
  "JavaFxRectangle" should {
    "extend JavaFxNode" in {
      SubtypeTest[JavaFxRectangle <:< JavaFxNode]
    }
    "call toolkit.createPGRectangle " resetToolkitMock {
      javaFxRectangle.internalNode must beAnInstanceOf[PGRectangle]
      there was one(stubToolkitMock).createPGRectangle()
    }
  }
}