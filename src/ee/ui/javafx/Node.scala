package ee.ui.javafx

import com.sun.javafx.sg.PGNode
import ee.ui.javafx.nativeImplementation.NativeImplementation
import com.sun.javafx.geom.transform.Affine3D
import ee.ui.properties.PropertyChangeCollector
import ee.ui.properties.PropertyChangeCollector._

abstract class Node(val implemented: ee.ui.Node) extends NativeImplementation {

  val internalNode: PGNode

  def update = {
    println("Node update")
    propertyChanges.applyChanges
  }
  
  val matrix = new Affine3D()

  private val propertyChanges = new PropertyChangeCollector(
    (implemented.x, implemented.y) ~> { (x, y) =>
      println("translating to ", x, y)
      matrix.setToIdentity
      matrix translate (x, y)
      //this is problematic since this should not reflect x and y
      //internalNode setTransformMatrix matrix
    })
}