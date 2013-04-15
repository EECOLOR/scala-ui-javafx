package ee.ui.display

import com.sun.javafx.sg.PGNode
import ee.ui.display.implementation.contracts.NodeContract
import ee.ui.members.Signal

abstract class JavaFxNode(node:NodeContract, val internalNode:PGNode) {
  val dirty = Signal()
}