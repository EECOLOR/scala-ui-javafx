package ee.ui.implementation

import org.specs2.mutable.Specification
import ee.ui.display.Window
import ee.ui.display.JavaFxWindow
import org.specs2.mock.Mockito
import utils.SubtypeTest
import utils.SignatureTest
import scala.collection.mutable.MapLike
import org.specs2.specification.Scope
import ee.ui.implementation.contracts.WindowContract
import ee.ui.implementation.contracts.WindowContract
import ee.ui.implementation.contracts.WindowContract

object JavaFxWindowImplementationHandlerTest extends Specification with Mockito {

  xonly
  
  implicit val contractHandlers = new DefaultContractHandlers
  
  trait test extends Scope {
    val implHandler = new JavaFxWindowImplementationHandler()(contractHandlers = contractHandlers)
    def storedWindows = implHandler.contracts
    val windowContract = WindowContract(new Window)
  } 

  "JavaFxWindowImplementationHandler" should {
    "extend WindowImplementationHandler with ContractHandler" in {
      SubtypeTest[JavaFxWindowImplementationHandler <:< WindowImplementationHandler with ContractHandler[WindowContract, JavaFxWindow]]
    }
    "maintain window representations when show and hide are called" in new test {
      storedWindows must not haveKey (windowContract)
      implHandler show windowContract
      storedWindows must haveKey(windowContract)
      implHandler hide windowContract
      storedWindows must not haveKey (windowContract)
    }
    "call show on the JavaFxWindow instance" in new test {

      var showCalled = false
      val handler =
        new JavaFxWindowImplementationHandler {
          override val create = (windowContract: WindowContract) =>
            new JavaFxWindow(windowContract) {
              override def show() = showCalled = true
            }
        }

      handler show windowContract

      showCalled
    }
    "call hide on the JavaFxWindow instance" in new test {

      var hideCalled = false
      val handler =
        new JavaFxWindowImplementationHandler {
          override val create = (windowContract: WindowContract) =>
            new JavaFxWindow(windowContract) {
              override def hide() = hideCalled = true
            }
        }

      handler hide windowContract

      hideCalled
    }
  }
}