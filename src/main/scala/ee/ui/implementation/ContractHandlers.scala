package ee.ui.implementation

import ee.ui.display.JavaFxWindow
import ee.ui.implementation.contracts.WindowContract
import ee.ui.implementation.contracts.SceneContract
import ee.ui.display.JavaFxScene
import ee.ui.display.JavaFxNode
import ee.ui.display.implementation.contracts.NodeContract

trait ContractHandlers {
   val windows:ContractHandler[WindowContract, JavaFxWindow]
   val scenes:ContractHandler[(JavaFxWindow, SceneContract), JavaFxScene]
   val nodes:ContractHandler[NodeContract, JavaFxNode]
}