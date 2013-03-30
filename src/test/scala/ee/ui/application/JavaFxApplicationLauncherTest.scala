package ee.ui.application

import org.specs2.mutable.Specification
import ee.ui.display.Window
import java.util.concurrent.CountDownLatch
import scala.concurrent.duration._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import org.specs2.time.NoTimeConversions
import utils.ApplicationLauncherUtils

class JavaFxApplicationLauncherTest extends Specification with NoTimeConversions with ApplicationLauncherUtils {
  isolated

  class TestPlatformImplementation extends PlatformImplementation {
    def startup(callback: => Unit): Unit = callback
    def runAndWait(code: => Unit): Unit = code
    def exit(): Unit = {}
    def addFinishListener(finishListener: PlatformImplementation.FinishListener): Unit = {}
  }

  "JavaFxApplicationLauncher" should {
    "have a platform implmentation" in {
      def test(j: JavaFxApplicationLauncher) =
        j.platformImplementation: PlatformImplementation
      ok
    }
    "call platformImplementation.startup" in {
      val startupCalled = WaitingBoolean(false)

      val launcher =
        new JavaFxApplicationLauncher {
          override val platformImplementation = new TestPlatformImplementation {
            override def startup(callback: => Unit) = {
              startupCalled set true
              super.startup(callback)
            }
          }
          def createApplication() = new Application with Engine {
            def start(window: Window) = {}
          }
        }

      startAndExitApplication(launcher)

      startupCalled.get === true
    }
    "call platformImplementation.addFinishListener" in {
       val addFinishListenerCalled = WaitingBoolean(false)

      val launcher =
        new JavaFxApplicationLauncher {
          override val platformImplementation = new TestPlatformImplementation {
            override def addFinishListener(finishListener: PlatformImplementation.FinishListener) = {
              addFinishListenerCalled set true
            }
          }
          def createApplication() = new Application with Engine {
            def start(window: Window) = {}
          }
        }

      startAndExitApplication(launcher)

      addFinishListenerCalled.get === true
    }
    
  }
}