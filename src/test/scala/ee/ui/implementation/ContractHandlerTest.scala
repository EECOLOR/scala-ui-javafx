package ee.ui.implementation

import org.specs2.mutable.Specification
import utils.SignatureTest
import org.specs2.specification.Scope
import utils.SubtypeTest

object ContractHandlerTest extends Specification {

  xonly

  trait test extends Scope {
    class TestContract
    class TestImplementation

    class TestContractHandler extends ContractHandler[TestContract, TestImplementation] {
      
      val create = (contract:TestContract) => new TestImplementation
    }
    
    val contractHandler = new TestContractHandler
    def storedContracts = contractHandler.contracts
    val contract = new TestContract
  }

  "ContractHandler" should {
    "extend ContractType => ImplementationType" in new test {
      SubtypeTest[ContractHandler[TestContract, TestImplementation] <:< (TestContract => TestImplementation)]
    }
    "maintain contract representations" in new test {
      val impl1: TestImplementation = contractHandler(contract)
      storedContracts must haveKey(contract)
      val impl2: TestImplementation = contractHandler(contract)
      impl1 == impl2
    }
    "use the factory" in new test {
      val impl = new TestImplementation
      val handler =
        new TestContractHandler {
          override val create = (contract: TestContract) => impl
        }
      handler(contract) must be(impl)
    }
  }

}