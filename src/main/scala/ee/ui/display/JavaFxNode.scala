package ee.ui.display

import com.sun.javafx.sg.PGNode
import ee.ui.display.implementation.contracts.NodeContract
import ee.ui.members.Signal
import com.sun.javafx.geom.transform.Affine3D
import ee.ui.implementation.Converter
import ee.ui.primitives.Transformation
import com.sun.javafx.geom.BaseBounds
import ee.ui.members.Property
import ee.ui.members.ReadOnlyProperty
import ee.ui.primitives.Bounds

abstract class JavaFxNode(node: NodeContract, val internalNode: PGNode) {
  private val _dirty = Property(true)
  def dirty: ReadOnlyProperty[Boolean] = _dirty
  def dirty_=(value: Boolean) = _dirty.value = value

  def boundsTransformed(bounds: Bounds) = {
    val fxBounds = Converter convert bounds
    internalNode setTransformedBounds fxBounds
  }

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

    node.bounds bindWith boundsTransformed

    (node.bounds.change | node.totalTransformation.change) {
      dirty = true
    }
  }
}