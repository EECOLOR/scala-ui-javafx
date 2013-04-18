package ee.ui.display

import com.sun.javafx.sg.PGNode
import ee.ui.display.implementation.contracts.NodeContract
import ee.ui.members.Signal
import com.sun.javafx.geom.transform.Affine3D
import ee.ui.implementation.Converter
import ee.ui.primitives.Transformation

abstract class JavaFxNode(node: NodeContract, val internalNode: PGNode) {
  val dirty = Signal()

  val bindToNode: Unit = {
    
    node.totalTransformation bindWith { totalTransformation =>
      
        val t = totalTransformation

        val matrix = new Affine3D
        matrix concatenate (
          t.xx, t.xy, t.xz, t.xt,
          t.yx, t.yy, t.yz, t.yt,
          t.zx, t.zy, t.zz, t.zt)

        internalNode setTransformMatrix matrix
    }
    
    node.bounds bindWith { bounds => 
        
        val fxBounds = Converter convert bounds
        
        internalNode setTransformedBounds fxBounds
    }
  }
}