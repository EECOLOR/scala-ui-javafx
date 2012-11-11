package ee.ui.javafx

import com.sun.javafx.sg.PGNode
import ee.ui.javafx.nativeImplementation.NativeImplementation
import com.sun.javafx.geom.transform.Affine3D
import ee.ui.properties.PropertyChangeCollector
import ee.ui.properties.PropertyGroup._
import ee.ui.primitives.Affine
import ee.ui.primitives.Rotate
import ee.ui.primitives.Scale
import ee.ui.primitives.Shear
import ee.ui.primitives.Translate
import ee.ui.primitives.Transformation
import ee.ui.javafx.nativeImplementation.Converters

abstract class Node(val implemented: ee.ui.Node) extends NativeImplementation {

  val internalNode: PGNode

  def update = {
    //println("Node update", internalNode.getClass.getName)
    propertyChanges.applyIfChanged
  }

  val matrix = new Affine3D

  private val propertyChanges = new PropertyChangeCollector(
    (implemented.totalTransformation, implemented.bounds) ~~> {
        (totalTransformation, bounds) =>
          
          //TODO add bounds
          matrix.setToIdentity
          
          val t = totalTransformation
          matrix concatenate (
            t.xx, t.xy, t.xz, t.xt,
            t.yx, t.yy, t.yz, t.yt,
            t.zx, t.zy, t.zz, t.zt)

          val fxBounds = Converters convertBounds bounds
          
          internalNode setTransformMatrix matrix
          internalNode setContentBounds fxBounds
          //TODO this should probably only reflect the changes that have been made (maybe only usefull for groups)
          internalNode setTransformedBounds fxBounds
      })
  propertyChanges.changed = true
}