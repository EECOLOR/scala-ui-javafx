package ee.ui.implementation

import org.specs2.mutable.Specification
import utils.SubtypeTest
import ee.ui.display.JavaFxNode
import ee.ui.display.shapes.JavaFxRectangle
import ee.ui.display.implementation.contracts.NodeContract
import ee.ui.display.implementation.contracts.RectangleContract

object JavaFxNodeHandlerTest extends Specification {
  
  xonly
  
  val nodeHandler = new JavaFxNodeHandler
  
  "JavaFxNodeHandler" should {
    "extend the correct types" in {
      SubtypeTest[JavaFxNodeHandler <:< ContractHandler[NodeContract, JavaFxNode]]
    }
    "create the correct instances" in {
      nodeHandler.create(new RectangleContract {}) must beAnInstanceOf[JavaFxRectangle]
    }
  }
}