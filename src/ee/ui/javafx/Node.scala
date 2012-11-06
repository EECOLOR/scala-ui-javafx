package ee.ui.javafx

import com.sun.javafx.sg.PGNode
import ee.ui.javafx.nativeImplementation.NativeImplementation
import com.sun.javafx.geom.transform.Affine3D
import ee.ui.properties.PropertyChangeCollector
import ee.ui.properties.PropertyChangeCollector._
import ee.ui.primitives.Affine
import ee.ui.primitives.Rotate
import ee.ui.primitives.Scale
import ee.ui.primitives.Shear
import ee.ui.primitives.Translate
import ee.ui.primitives.Transformation

abstract class Node(val implemented: ee.ui.Node) extends NativeImplementation {

  val internalNode: PGNode

  def update = {
    println("Node update", internalNode.getClass.getName)
    propertyChanges.applyChanges
  }

  val matrix = new Affine3D

  val ignorePosition = false

  private val propertyChanges = new PropertyChangeCollector(
    (implemented.x, implemented.y, implemented.totalTransformation) ~> {
        (x, y, totalTransformation) =>
          
          //TODO add bounds
          matrix.setToIdentity
          
          if (ignorePosition) matrix translate (-x, -y, 0)
          
          val t = totalTransformation
          matrix concatenate (
            t.xx, t.xy, t.xz, t.xt,
            t.yx, t.yy, t.yz, t.yt,
            t.zx, t.zy, t.zz, t.zt)

          internalNode setTransformMatrix matrix
      })
}