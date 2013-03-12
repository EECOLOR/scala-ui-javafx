package ee.ui.javafx

import com.sun.javafx.sg.PGNode
import ee.ui.javafx.nativeImplementation.NativeImplementation
import com.sun.javafx.geom.transform.Affine3D
import ee.ui.primitives.Affine
import ee.ui.primitives.Rotate
import ee.ui.primitives.Scale
import ee.ui.primitives.Shear
import ee.ui.primitives.Translate
import ee.ui.primitives.Transformation
import ee.ui.javafx.nativeImplementation.Converters
import ee.ui.members.Property

abstract class Node(val implemented: ee.ui.display.Node) extends NativeImplementation {

  val internalNode: PGNode

  val matrix = new Affine3D

  (implemented.totalTransformation | implemented.bounds) ~> {
    case (totalTransformation, bounds) =>
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
  }
}