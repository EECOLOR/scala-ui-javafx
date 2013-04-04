package ee.ui.application

import org.specs2.mutable.Specification
import utils.ThreadUtils
import scala.concurrent.duration._
import org.specs2.time.NoTimeConversions
import com.sun.javafx.pgstub.StubStage
import scala.collection.JavaConverters._
import com.sun.javafx.tk.TKStage
import com.sun.javafx.application.PlatformImpl
import org.specs2.mock.Mockito
import java.util.concurrent.CountDownLatch
import ee.ui.implementation.StubToolkit
import org.specs2.specification.Example
import org.specs2.execute.AsResult

class DefaultPlatformImplementationTest extends Specification with ThreadUtils with NoTimeConversions with StubToolkit with Mockito {

  /* we need to execute sequential because DefaultPlatformImplementation 
   * is backed by a static (stateful) class
   */
  sequential
  isolated

  val platformImplementation = new DefaultPlatformImplementation

  "DefaultPlatformImplementation" should {
    "be used with StubToolkit" in {
      toolkit must beAnInstanceOf[test.toolkit.StubToolkit]
    }
    "call the callback when startup is called" in {
      val callbackCalled = WaitingBoolean(false)

      platformImplementation startup {
        callbackCalled set true
      }

      platformImplementation.exit()

      callbackCalled.get === true
    }
    "signal onIdle when all windows are hidden" in {
      var idleCalled = false

      platformImplementation startup {}

      val subscription =
        platformImplementation onIdle {
          idleCalled = true
        }

      /*
       * In order for idle to trigger, we need to open and close a window
       */
      toolkit.notifyWindowListeners(List[TKStage](new StubStage).asJava)
      toolkit.notifyWindowListeners(List.empty[TKStage].asJava)

      subscription.unsubscribe()

      platformImplementation.exit()

      idleCalled
    }
    "call exit on the toolkit if exit is called" resetToolkitMock {
      platformImplementation startup {}
      there was no(stubToolkitMock).exit()
      platformImplementation.exit()
      there was one(stubToolkitMock).exit()
    }
    "call defer on the toolkit if run is called" resetToolkitMock {
      platformImplementation startup {}
      there was no(stubToolkitMock).defer(any[Runnable])
      platformImplementation run {}
      there was one(stubToolkitMock).defer(any[Runnable])
      platformImplementation.exit()
      ok
    }
    "throw an exception if startup is called twice" in {
      platformImplementation startup {}
      platformImplementation startup {} must throwA[RuntimeException]
      platformImplementation.exit()
    }
    "wait until code is complete" in {
      val checkWait = new CountDownLatch(1)
      var waiting = true
      val code =
        inThread {
          platformImplementation runAndWait {
            checkWait.await()
          }
          waiting = false
        }

      Thread.sleep(300)

      waiting === true

      checkWait.countDown()

      waitFor(code)

      !waiting
    }
    "not signal onIdle when exit has been called" in {
      var idleCalled = false

      platformImplementation startup {}

      val subscription =
        platformImplementation onIdle {
          idleCalled = true
        }

      platformImplementation.exit()

      /*
       * In order for idle to trigger, we need to open and close a window
       */
      toolkit.notifyWindowListeners(List[TKStage](new StubStage).asJava)
      toolkit.notifyWindowListeners(List.empty[TKStage].asJava)

      subscription.unsubscribe()

      !idleCalled
    }
  }
}