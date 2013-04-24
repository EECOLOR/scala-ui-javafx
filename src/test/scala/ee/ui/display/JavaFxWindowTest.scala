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
import scala.util.Random
import com.sun.javafx.tk.TKStageListener
import ee.ui.implementation.contracts.WindowContract
import org.mockito.{ Mockito => MockitoLibrary }
import ee.ui.implementation.DefaultContractHandlers
import ee.ui.implementation.SpyContractHandlers
import java.security.AccessControlContext

class JavaFxWindowTest extends Specification with StubToolkit with Mockito {
  
  sequential
  isolated
  xonly

  val contractHandlers = new SpyContractHandlers
  def javaFxWindow(window: Window): JavaFxWindow = JavaFxWindow(contract = WindowContract(window))(contractHandlers = contractHandlers)
  lazy val javaFxWindow: JavaFxWindow = javaFxWindow(new Window)

  "JavaFxWindow" should {
    
    "have a TK representation" in {
      SignatureTest[JavaFxWindow, TKStage](_.internalWindow)
    }
    
    "call the toolkit to create a TK representation" resetToolkitMock {
      javaFxWindow.internalWindow must beAnInstanceOf[TKStage]
      there was one(stubToolkitMock).createTKStage(StageStyle.DECORATED, true, Modality.NONE, null, true)
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
    
    "call internalWindow.setSecurityContext" in {
      there was one(javaFxWindow.internalWindow).setSecurityContext(any[AccessControlContext])
    }
    
    "make a call to setTitle of the stage" in {
      val t = "test"
      val window = new Window {
        title = t
      }
      val fxWindow = javaFxWindow(window)
      there was one(fxWindow.internalWindow).setTitle(t)
    }
    
    "set the correct size" in {
      val w1 = Random.nextInt
      val w2 = Random.nextInt
      val h1 = Random.nextInt
      val h2 = Random.nextInt
      val window = new Window {
        width = w1
        height = h1
      }
      val fxWindow = javaFxWindow(window)
      there was one(fxWindow.internalWindow).setBounds(0f, 0f, false, false, w1, h1, -1f, -1f, 0f, 0f)
      window.width = w2
      window.height = h2
      there was one(fxWindow.internalWindow).setBounds(0f, 0f, false, false, w2, h2, -1f, -1f, 0f, 0f)
    }
    
    "add a listener to the internal window" in {
      there was one(javaFxWindow.internalWindow).setTKStageListener(any)
    }

    "reflect external size changes in the window" in {
      val window = new Window
      val fxWindow = javaFxWindow(window)
      var listener = fxWindow.internalWindow.asInstanceOf[StubStage].stageListener

      MockitoLibrary reset fxWindow.internalWindow

      listener.changedSize(1, 2)
      window.width.value === 1
      window.height.value === 2
      there was no(fxWindow.internalWindow).setBounds(anyFloat, anyFloat, any[Boolean], any[Boolean], anyFloat, anyFloat, anyFloat, anyFloat, anyFloat, anyFloat)
    }

    "react to only the width change" in {
      val w1 = Random.nextInt
      val h1 = Random.nextInt
      val w2 = Random.nextInt
      val w3 = Random.nextInt
      val window = new Window {
        width = w1
      }
      val fxWindow = javaFxWindow(window)
      var listener = fxWindow.internalWindow.asInstanceOf[StubStage].stageListener
      there was one(fxWindow.internalWindow).setBounds(0f, 0f, false, false, w1, 0f, -1f, -1f, 0f, 0f)
      listener.changedSize(1, h1)
      window.width = w2
      there was one(fxWindow.internalWindow).setBounds(0f, 0f, false, false, w2, h1, -1f, -1f, 0f, 0f)
      window.width = w3
      there was one(fxWindow.internalWindow).setBounds(0f, 0f, false, false, w3, h1, -1f, -1f, 0f, 0f)
    }

    "call internalWindow.setScene" in {
      val window1 = new Window { scene = new Scene }
      val fxWindow1 = javaFxWindow(window1)
      val fxScene1 = contractHandlers.scenes(fxWindow1 -> window1.scene.get)
      val window2 = new Window { scene = None }
      val fxWindow2 = javaFxWindow(window2)
      there was one(fxWindow1.internalWindow).setScene(fxScene1.internalScene)
      there was no(fxWindow2.internalWindow).setScene(any)
    }
    "handle scene changes correctly" in {
      val window = new Window { scene = None }
      val fxWindow = javaFxWindow(window)
      val internalWindow = fxWindow.internalWindow
      there was no(internalWindow).setScene(any)
      
      val scene1 = new Scene
      window.scene = scene1
      val fxScene1 = contractHandlers.scenes(fxWindow -> scene1).internalScene
      there was one(internalWindow).setScene(fxScene1)
      
      val scene2 = new Scene
      window.scene = scene2 
      there was one(contractHandlers.scenes).removeContract(fxWindow -> scene1)
      val fxScene2 = contractHandlers.scenes(fxWindow -> scene2).internalScene
      there was one(internalWindow).setScene(fxScene2)
      
      window.scene = None 
      there was one(contractHandlers.scenes).removeContract(fxWindow -> scene2)
      there was one(internalWindow).setScene(null)
    }

  }
}