package ee.ui.implementation

import org.specs2.mutable.Specification
import utils.SubtypeTest
import ee.ui.display.JavaFxNode
import ee.ui.display.shapes.JavaFxRectangle
import ee.ui.display.implementation.contracts.NodeContract
import ee.ui.display.implementation.contracts.RectangleContract
import ee.ui.display.implementation.contracts.TextContract
import ee.ui.display.shapes.JavaFxText

object JavaFxNodeHandlerTest extends Specification {

  xonly

  val nodeHandler = new JavaFxNodeHandler

  "JavaFxNodeHandler" should {
    "extend the correct types" in {
      SubtypeTest[JavaFxNodeHandler <:< ContractHandler[NodeContract, JavaFxNode]]
    }
    "create the correct instances" in {
      nodeHandler.create(new RectangleContract {}) must beAnInstanceOf[JavaFxRectangle]
      nodeHandler.create(new TextContract {}) must beAnInstanceOf[JavaFxText]
    }
  }
}