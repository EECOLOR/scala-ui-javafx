package ee.ui.display

import org.specs2.mutable.Specification
import utils.SignatureTest
import com.sun.javafx.tk.TKStage
import test.toolkit.StubStage

object JavaFxWindowTest extends Specification {
  "JavaFxWindow" should {
    "represent a window" in {
      new JavaFxWindow(window = new Window)
      ok
    }
    "have a TK representation" in {
      SignatureTest[JavaFxWindow, TKStage](_.internalWindow)
    }
    "have a show method which should" in {
      "have the corect signature" in {
        SignatureTest[JavaFxWindow, Unit](_.show())
      }
      "set visible to true of the internal window" in {
        val javaFxWindow = new JavaFxWindow(new Window)
        javaFxWindow.show()
        javaFxWindow.internalWindow.asInstanceOf[StubStage].visible
      }
    }
    "have a hide method which should" in {
      "have the correct signature" in {
        SignatureTest[JavaFxWindow, Unit](_.hide())
      }
      "set visible to false of the internal window" in {
        val javaFxWindow = new JavaFxWindow(new Window)
        javaFxWindow.hide()
        !javaFxWindow.internalWindow.asInstanceOf[StubStage].visible
      }
    }
  }
}