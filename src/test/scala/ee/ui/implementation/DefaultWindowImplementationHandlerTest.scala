package ee.ui.implementation

import org.specs2.mutable.Specification
import ee.ui.display.Window
import ee.ui.display.JavaFxWindow
import ee.ui.display.JavaFxWindow
import org.specs2.mock.Mockito
import ee.ui.display.JavaFxWindow
import ee.ui.display.JavaFxWindow
import utils.SubtypeTest
import utils.SignatureTest
import ee.ui.display.JavaFxWindow
import ee.ui.display.JavaFxWindow
import scala.collection.mutable.MapLike
import org.specs2.specification.Scope

object DefaultWindowImplementationHandlerTest extends Specification with Mockito {

  trait test extends Scope {
    val implHandler = new DefaultWindowImplementationHandler
    def storedWindows = implHandler.windows
    val window = new Window
  } 

  "DefaultWindowImplementationHandler" should {
    "extend WindowImplementationHandler" in {
      SubtypeTest[DefaultWindowImplementationHandler <:< WindowImplementationHandler]
    }
    "have a factory method for JavaFxWindow" in new test {
      SignatureTest[DefaultWindowImplementationHandler, JavaFxWindow](_.createWindow(window))
    }
    "maintain window representations" in new test {
      val javaFxWindow1: JavaFxWindow = implHandler(window)
      storedWindows must haveKey(window)
      val javaFxWindow2: JavaFxWindow = implHandler(window)
      javaFxWindow1 == javaFxWindow2
    }
    "use the factory" in new test {
      val javaFxWindow = new JavaFxWindow(window)
      val handler =
        new DefaultWindowImplementationHandler {
          override val createWindow = (window: Window) => javaFxWindow
        }
      handler(window) must be(javaFxWindow)
    }
    "maintain window representations when show and hide are called" in new test {
      storedWindows must not haveKey (window)
      implHandler show window
      storedWindows must haveKey(window)
      implHandler hide window
      storedWindows must not haveKey (window)
    }
    "call show on the JavaFxWindow instance" in new test {

      var showCalled = false
      val handler =
        new DefaultWindowImplementationHandler {
          override val createWindow = (window: Window) =>
            new JavaFxWindow(window) {
              override def show() = showCalled = true
            }
        }

      handler show window

      showCalled
    }
    "call hide on the JavaFxWindow instance" in new test {

      var hideCalled = false
      val handler =
        new DefaultWindowImplementationHandler {
          override val createWindow = (window: Window) =>
            new JavaFxWindow(window) {
              override def hide() = hideCalled = true
            }
        }

      handler hide window

      hideCalled
    }
  }
}