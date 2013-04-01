package ee.ui.application

import org.specs2.mutable.Specification
import ee.ui.display.Window
import java.util.concurrent.CountDownLatch
import scala.concurrent.duration._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import org.specs2.time.NoTimeConversions
import utils.ApplicationLauncherUtils
import ee.ui.members.detail.Subscription
import utils.SubtypeTest
import com.sun.javafx.tk.Toolkit
import com.sun.javafx.pgstub.StubToolkit
import com.sun.javafx.application.PlatformImpl

class JavaFxApplicationLauncherTest extends Specification with NoTimeConversions with ApplicationLauncherUtils {
  sequential

  class TestPlatformImplementation extends PlatformImplementation {
    def startup(callback: => Unit): Unit = callback
    def runAndWait(code: => Unit): Unit = code
    def exit(): Unit = {}
    def addFinishListener(finishListener: PlatformImplementation.FinishListener): Subscription = new Subscription { def unsubscribe() = {} }
  }

  "JavaFxApplicationLauncher" should {
    "have a platform implmentation" in {
      SubtypeTest[JavaFxApplicationLauncher <:< PlatformImplementation]
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
              super.addFinishListener(finishListener)
            }
          }
          def createApplication() = new Application with Engine {
            def start(window: Window) = {}
          }
        }

      startAndExitApplication(launcher)

      addFinishListenerCalled.get === true
    }
    "call application start on the JavaFx application thread" in {
      var correctThread = false
      val launcher =
        new JavaFxApplicationLauncher {
          def createApplication() = new Application with Engine {
            def start(window: Window) = {
              correctThread = Toolkit.getToolkit.isFxUserThread
              exit()
            }
          }
        }

      //startAndExitApplication(launcher, 5.seconds)

      //correctThread
      todo
    }
    "call exit when the application has hidden it's last window" in {
      val exitCalled = WaitingBoolean(false)

      val launcher =
        new JavaFxApplicationLauncher {
          override def exit(application:Application) = {
            exitCalled set true
            super.exit(application)
          } 
        
          def createApplication() = new Application with Engine {
            def start(window: Window) = {
               show(window)
               hide(window)
            }
          }
        }

      //start(launcher)

      //exitCalled.get
      todo
    }
    "exit the application if the signal comes from outside" in {
      //pretend a window was closed by the user
      todo
    }
  }
}