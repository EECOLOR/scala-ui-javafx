package ee.ui.implementation

import org.specs2.mutable.Specification
import utils.SubtypeTest

object DefaultContractHandlersTest extends Specification {
  "DefaultContractHandlers" should {
    "extend ContractHandlers" in {
      SubtypeTest[DefaultContractHandlers <:< ContractHandlers]
    }
  }
}