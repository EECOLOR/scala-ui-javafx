package ee.ui.display.shapes

import ee.ui.display.JavaFxNode
import ee.ui.display.implementation.contracts.RectangleContract
import com.sun.javafx.sg.PGRectangle
import ee.ui.implementation.Toolkit

case class JavaFxRectangle(rectangle:RectangleContract) extends JavaFxNode with Toolkit {
  lazy val internalNode:PGRectangle = 
    toolkit.createPGRectangle()
}