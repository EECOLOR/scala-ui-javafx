package ee.ui.display

import org.specs2.mutable.Specification
import utils.SignatureTest
import com.sun.javafx.tk.TKStage
import test.toolkit.StubStage
import ee.ui.implementation.Toolkit
import ee.ui.implementation.StubToolkit
import org.specs2.mock.Mockito
import javafx.stage.StageStyle
import javafx.stage.Modality

class JavaFxWindowTest extends Specification with StubToolkit with Mockito {
  sequential
  isolated
  
  def javaFxWindow(window:Window):JavaFxWindow = new JavaFxWindow(window = window)
  val javaFxWindow:JavaFxWindow = javaFxWindow(new Window)
  
  "JavaFxWindow" should {
    "have a TK representation" in {
      SignatureTest[JavaFxWindow, TKStage](_.internalWindow)
    }
    "call the toolkit to create a TK representation" resetToolkitMock {
      javaFxWindow.internalWindow
      there was one(stubToolkitMock).createTKStage(StageStyle.DECORATED, true, Modality.NONE, null)
    }
    "have a show method which should" in {
      "have the corect signature" in {
        SignatureTest[JavaFxWindow, Unit](_.show())
      }
      "set visible to true of the internal window" in {
        javaFxWindow.show()
        there was one(javaFxWindow.internalWindow).setVisible(true)
      }
    }
    "have a hide method which should" in {
      "have the correct signature" in {
        SignatureTest[JavaFxWindow, Unit](_.hide())
      }
      "set visible to false of the internal window" in {
        javaFxWindow.hide()
        there was one(javaFxWindow.internalWindow).setVisible(false)
      }
    }
    "was a call to setTitle of the stage" in {
      val title = "test"
      val window = new Window
      window.title = title
      val fxWindow = javaFxWindow(window)
      there was one(fxWindow.internalWindow).setTitle(title)
    }
  }
}