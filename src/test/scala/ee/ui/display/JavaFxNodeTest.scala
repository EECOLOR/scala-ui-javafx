package ee.ui.display

import org.specs2.mutable.Specification
import utils.TestUtils
import scala.tools.reflect.ToolBoxError
import utils.SignatureTest
import com.sun.javafx.sg.PGNode
import ee.ui.display.implementation.contracts.NodeContract
import ee.ui.display.shapes.Rectangle
import com.sun.javafx.pgstub.StubNode

object JavaFxNodeTest extends Specification {

  xonly
  
  "JavaFxNode" should {
    
    "be abstract" in {
      TestUtils.eval("new ee.ui.display.JavaFxNode") must throwA[ToolBoxError].like {
        case e => e.getMessage must contain("class JavaFxNode is abstract")
      }
    }
    
    "have an internalNode property" in {
      SignatureTest[JavaFxNode, PGNode](_.internalNode)
    }
    
    "have a NodeContract passed in" in {
      val node:NodeContract = new Rectangle
      new JavaFxNode(node = node, new StubNode) {}
      ok
    }
  }
}