package ee.ui.implementation

import ee.ui.display.JavaFxWindow
import ee.ui.implementation.contracts.WindowContract
import ee.ui.implementation.contracts.SceneContract
import ee.ui.display.JavaFxScene

trait ContractHandlers {
   val windows:ContractHandler[WindowContract, JavaFxWindow]
   val scenes:ContractHandler[SceneContract, JavaFxScene]
}