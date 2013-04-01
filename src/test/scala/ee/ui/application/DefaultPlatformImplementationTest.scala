package ee.ui.application

import org.specs2.mutable.Specification
import utils.ThreadUtils
import PlatformImplementation.FinishListener
import scala.concurrent.duration._
import org.specs2.time.NoTimeConversions
import com.sun.javafx.tk.Toolkit
import com.sun.javafx.pgstub.StubToolkit
import com.sun.javafx.pgstub.StubStage
import scala.collection.JavaConverters._
import com.sun.javafx.tk.TKStage
import com.sun.javafx.application.PlatformImpl

class DefaultPlatformImplementationTest extends Specification with ThreadUtils with NoTimeConversions {

  /* we need to execute sequential because DefaultPlatformImplementation 
   * is backed by a static (stateful) class
   */
  sequential

  "DefaultPlatformImplementation" should {
    "be used with StubToolkit" in {
      Toolkit.getToolkit() must beAnInstanceOf[StubToolkit]
    }
    "call the callback when startup is called" in {
      val callbackCalled = WaitingBoolean(false)

      DefaultPlatformImplementation startup {
        callbackCalled set true
      }

      callbackCalled.get === true
    }
    "call the FinishListener on idle" in {
      var idleCalled = false

      val listener = DefaultPlatformImplementation addFinishListener new FinishListener {
        def idle() = idleCalled = true
        def exit() = ???
      }

      /*
       * In order for idle to trigger, we need to open and close a window
       */
      val toolkit = Toolkit.getToolkit()
      toolkit.notifyWindowListeners(List[TKStage](new StubStage).asJava)
      toolkit.notifyWindowListeners(List.empty[TKStage].asJava)

      listener.unsubscribe()

      idleCalled
    }
    "call the FinishListener on exit" in {
      var exitCalled = false

      val listener =
        DefaultPlatformImplementation addFinishListener new FinishListener {
          def idle() = ???
          def exit() = exitCalled = true
        }

      DefaultPlatformImplementation.exit()

      listener.unsubscribe()

      exitCalled
    }
  }
}