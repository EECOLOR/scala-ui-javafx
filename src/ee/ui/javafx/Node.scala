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

  private val propertyChanges = new PropertyChangeCollector(
    (implemented.x, implemented.y,
      implemented.translateX, implemented.translateY, implemented.translateZ,
      implemented.rotation, implemented.rotationAxis,
      implemented.scaleX, implemented.scaleY, implemented.scaleZ) ~> {
        (x, y,
        translateX, translateY, translateZ,
        rotation, rotationAxis,
        scaleX, scaleY, scaleZ) =>

          matrix.setToIdentity

          val (newX, newY) =
            if (addPositionToTranslatePosition) (x + translateX, y + translateY)
            else (translateX, translateY)

          if (scaleX != 1 || scaleY != 1 || scaleZ != 1 || rotation != 0) {
        	val pivotX = implemented.width / 2d + implemented.x  
        	val pivotY = implemented.height / 2d + implemented.y
        	
        	matrix translate (newX + pivotX, newY + pivotY, translateZ)
            matrix rotate (math toRadians rotation, rotationAxis.x, rotationAxis.y, rotationAxis.z)
        	matrix scale (scaleX, scaleY, scaleZ)
        	matrix translate (-pivotX, -pivotY, 0)
        	
          } else 
            matrix translate (newX, newY, translateZ)

          //TODO add a listener for the transforms
          implemented.transforms foreach applyTransformation
          
          //TODO add bounds
          
          internalNode setTransformMatrix matrix
      })
}