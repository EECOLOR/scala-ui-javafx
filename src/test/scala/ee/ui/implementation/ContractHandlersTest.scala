package ee.ui.implementation

import org.specs2.mutable.Specification
import utils.SignatureTest
import ee.ui.display.Window
import ee.ui.display.JavaFxWindow
import ee.ui.implementation.contracts.WindowContract
import ee.ui.implementation.contracts.SceneContract
import ee.ui.display.JavaFxScene

object ContractHandlersTest extends Specification {
  "ContractHandlers" should {
    "have a handler for windows" in {
      SignatureTest[ContractHandlers, ContractHandler[WindowContract, JavaFxWindow]](_.windows)
    }
    "have a handler for scenes" in {
      SignatureTest[ContractHandlers, ContractHandler[SceneContract, JavaFxScene]](_.scenes)
    }
  }
}