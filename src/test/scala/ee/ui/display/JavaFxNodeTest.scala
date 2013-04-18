package ee.ui.display

import org.specs2.mutable.Specification
import utils.TestUtils
import scala.tools.reflect.ToolBoxError
import utils.SignatureTest
import com.sun.javafx.sg.PGNode
import ee.ui.display.implementation.contracts.NodeContract
import ee.ui.display.shapes.Rectangle
import com.sun.javafx.pgstub.StubNode
import ee.ui.members.Signal
import ee.ui.implementation.StubToolkit
import org.specs2.mock.Mockito
import com.sun.javafx.geom.transform.Affine3D
import ee.ui.primitives.transformation.Affine
import ee.ui.display.traits.Transformations
import ee.ui.primitives.Transformation
import ee.ui.display.traits.Size
import ee.ui.display.traits.Position
import ee.ui.primitives.Bounds
import ee.ui.implementation.Converter

class JavaFxNodeTest extends Specification with Mockito {

  xonly
  isolated
  sequential

  val internalNode = spy(new StubNode)
  val node: NodeContract with Transformations with Position with Size = new Rectangle with Transformations with Position with Size
  val javaFxNode = new JavaFxNode(node = node, internalNode) {}

  def matrixFor(t: Transformation) = {
    val matrix = new Affine3D
    matrix.setToIdentity()
    matrix concatenate (
      t.xx, t.xy, t.xz, t.xt,
      t.yx, t.yy, t.yz, t.yt,
      t.zx, t.zy, t.zz, t.zt)
    matrix
  }

  "JavaFxNode" should {

    "be abstract" in {
      TestUtils.eval("new ee.ui.display.JavaFxNode") must throwA[ToolBoxError].like {
        case e => e.getMessage must contain("class JavaFxNode is abstract")
      }
    }

    "have an internalNode property" in {
      SignatureTest[JavaFxNode, PGNode](_.internalNode)
    }

    "have a dirty signal" in {
      SignatureTest[JavaFxNode, Signal](_.dirty)
    }

    "react to changes in the totalTransformation" in {

      val t = new Affine(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

      node.transformations += t
      node.totalTransformation.value === t

      there was one(internalNode).setTransformMatrix(matrixFor(t))
      
      node.transformations += t
      node.totalTransformation.value === (t ++ t)
      
      there was one(internalNode).setTransformMatrix(matrixFor(t ++ t))
    }
    
    "react to changes in the bounds" in {
      
      val (x1, y1, width1, height1) = (1, 2, 3, 4)
      
      node.x = x1
      node.y = y1
      node.width = width1
      node.height = height1
      val b1 = Bounds(1, 2, 4, 6)
      
      there was one(internalNode).setTransformedBounds(Converter convert b1)
      
      val (x2, y2, width2, height2) = (2, 3, 4, 5)
      
      node.x = x2
      node.y = y2
      node.width = width2
      node.height = height2
      val b2 = Bounds(2, 3, 6, 8)
      
      there was one(internalNode).setTransformedBounds(Converter convert b2)
    }
    
    "should fire dirty" in {
      todo
    }
  }
}