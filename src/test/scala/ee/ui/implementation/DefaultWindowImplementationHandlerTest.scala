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

object DefaultWindowImplementationHandlerTest extends Specification with Mockito {

  xonly
  
  trait test extends Scope {
    val implHandler = new DefaultWindowImplementationHandler
    def storedWindows = implHandler.windowContracts
    val windowContract = WindowContract(new Window)
  } 

  "DefaultWindowImplementationHandler" should {
    "extend WindowImplementationHandler" in {
      SubtypeTest[DefaultWindowImplementationHandler <:< WindowImplementationHandler]
    }
    "have a factory method for JavaFxWindow" in new test {
      SignatureTest[DefaultWindowImplementationHandler, JavaFxWindow](_.createWindow(windowContract))
    }
    "maintain window representations" in new test {
      val javaFxWindow1: JavaFxWindow = implHandler(windowContract)
      storedWindows must haveKey(windowContract)
      val javaFxWindow2: JavaFxWindow = implHandler(windowContract)
      javaFxWindow1 == javaFxWindow2
    }
    "use the factory" in new test {
      val javaFxWindow = new JavaFxWindow(windowContract)
      val handler =
        new DefaultWindowImplementationHandler {
          override val createWindow = (windowContract: WindowContract) => javaFxWindow
        }
      handler(windowContract) must be(javaFxWindow)
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
        new DefaultWindowImplementationHandler {
          override val createWindow = (windowContract: WindowContract) =>
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
        new DefaultWindowImplementationHandler {
          override val createWindow = (windowContract: WindowContract) =>
            new JavaFxWindow(windowContract) {
              override def hide() = hideCalled = true
            }
        }

      handler hide windowContract

      hideCalled
    }
  }
}