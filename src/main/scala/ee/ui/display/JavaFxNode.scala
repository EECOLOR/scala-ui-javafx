package ee.ui.display

import com.sun.javafx.sg.PGNode
import ee.ui.display.implementation.contracts.NodeContract

abstract class JavaFxNode(node:NodeContract, val internalNode:PGNode) {
  
}