package ee.ui.implementation

import org.specs2.mutable.Specification
import utils.SubtypeTest
import utils.SignatureTest

object DefaultContractHandlersTest extends Specification {
  
  xonly
  
  "DefaultContractHandlers" should {
    "extend ContractHandlers" in {
      SubtypeTest[DefaultContractHandlers <:< ContractHandlers]
    }
    "use the correct ContractHandlers" in {
      SignatureTest[DefaultContractHandlers, JavaFxWindowHandler](_.windows)
      SignatureTest[DefaultContractHandlers, JavaFxSceneHandler](_.scenes)
      SignatureTest[DefaultContractHandlers, JavaFxNodeHandler](_.nodes)
    }
  }
}