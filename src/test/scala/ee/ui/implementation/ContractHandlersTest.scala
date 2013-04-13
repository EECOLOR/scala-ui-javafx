package ee.ui.implementation

import org.specs2.mutable.Specification
import utils.SignatureTest
import ee.ui.display.Window
import ee.ui.display.JavaFxWindow
import ee.ui.implementation.contracts.WindowContract
import ee.ui.implementation.contracts.SceneContract
import ee.ui.display.JavaFxScene
import ee.ui.display.JavaFxNode
import ee.ui.display.implementation.contracts.NodeContract

object ContractHandlersTest extends Specification {
  
  xonly
  
  "ContractHandlers" should {
    "have a handler for windows" in {
      SignatureTest[ContractHandlers, ContractHandler[WindowContract, JavaFxWindow]](_.windows)
    }
    "have a handler for scenes" in {
      SignatureTest[ContractHandlers, ContractHandler[(JavaFxWindow, SceneContract), JavaFxScene]](_.scenes)
    }
    "have a handler for nodeContracts" in {
      SignatureTest[ContractHandlers, ContractHandler[NodeContract, JavaFxNode]](_.nodes)
    }
  }
}