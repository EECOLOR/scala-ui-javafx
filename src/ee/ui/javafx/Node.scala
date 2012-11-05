package ee.ui.javafx

import com.sun.javafx.sg.PGNode
import ee.ui.javafx.nativeImplementation.NativeImplementation
import com.sun.javafx.geom.transform.Affine3D
import ee.ui.properties.PropertyChangeCollector
import ee.ui.properties.PropertyChangeCollector._
import ee.ui.primitives.Transform
import ee.ui.primitives.Affine
import ee.ui.primitives.Rotate
import ee.ui.primitives.Scale
import ee.ui.primitives.Shear
import ee.ui.primitives.Translate

abstract class Node(val implemented: ee.ui.Node) extends NativeImplementation {

  val internalNode: PGNode

  def update = {
    println("Node update", internalNode.getClass.getName)
    propertyChanges.applyChanges
  }

  val matrix = new Affine3D

  val addPositionToTranslatePosition = true

  def applyTransformation(t: Transform) = t match {

    case Affine(
      mxx, mxy, mxz, tx,
      myx, myy, myz, ty,
      mzx, mzy, mzz, tz) =>
      matrix concatenate (
        mxx, mxy, mxz, tx,
        myx, myy, myz, ty,
        mzx, mzy, mzz, tz)

    case Rotate(angle, axis, pivotX, pivotY, pivotZ) =>
      if (pivotX != 0 || pivotY != 0 || pivotZ != 0) {
        matrix translate (pivotX, pivotY, pivotZ)
        matrix rotate (Math toRadians angle, axis.x, axis.y, axis.z)
        matrix translate (-pivotX, -pivotY, -pivotZ)
      } else
        matrix.rotate(Math toRadians angle, axis.x, axis.y, axis.z)

    case Scale(x, y, z, pivotX, pivotY, pivotZ) =>
      if (pivotX != 0 || pivotY != 0 || pivotZ != 0) {
        matrix translate (pivotX, pivotY, pivotZ)
        matrix scale (x, y, z)
        matrix translate (-pivotX, -pivotY, -pivotZ)
      } else
        matrix scale (x, y, z)

    case Shear(x, y, pivotX, pivotY) =>
      if (pivotX != 0 || pivotY != 0) {
        matrix translate (pivotX, pivotY)
        matrix shear (x, y)
        matrix translate (-pivotX, -pivotY)
      } else
        matrix shear (x, y)

    case Translate(x, y, z) =>
      matrix.translate(x, y, z)
  }

  //pivotX = implemented.x + implemented.width / 2

  private val propertyChanges = new PropertyChangeCollector(
    (implemented.x, implemented.y) ~> { (x, y) =>
      //println("translating to ", x, y)
      //matrix.setToIdentity
      //matrix translate (x, y)
      //this is problematic since this should not reflect x and y
      //internalNode setTransformMatrix matrix
    })
}