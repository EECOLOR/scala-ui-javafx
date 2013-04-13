package ee.ui.implementation

import ee.ui.display.JavaFxNode
import ee.ui.display.detail.ReadOnlyNode
import ee.ui.display.Node
import ee.ui.display.shapes.JavaFxRectangle
import ee.ui.display.shapes.detail.ReadOnlyRectangle
import ee.ui.display.implementation.contracts.NodeContract
import ee.ui.display.implementation.contracts.RectangleContract

class JavaFxNodeHandler extends ContractHandler[NodeContract, JavaFxNode] {
  val create:NodeContract => JavaFxNode = {  
    case rectangle:RectangleContract => new JavaFxRectangle(rectangle)
  }
}