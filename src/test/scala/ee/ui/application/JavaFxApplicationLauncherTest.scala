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
import com.sun.javafx.application.PlatformImpl
import ee.ui.members.ReadOnlySignal
import org.specs2.mock.Mockito
import ee.ui.members.Signal
import utils.SignatureTest
import ee.ui.implementation.JavaFxWindowHandler
import com.sun.javafx.tk.TKStage
import ee.ui.implementation.Toolkit
import scala.collection.JavaConverters._

class JavaFxApplicationLauncherTest extends Specification with NoTimeConversions with ApplicationLauncherUtils with Toolkit with Mockito {
  
  sequential
  xonly

  "JavaFxApplicationLauncher" should {
    "have a platform implmentation" in {
      SignatureTest[JavaFxApplicationLauncher, PlatformImplementation](_.platformImplementation)
    }
    "have an engine with the correct windowImplementationHandler" in {
      val j = new JavaFxApplicationLauncher { def createApplication() = ??? }
      val e = new j.Engine {}
      e.windowImplementationHandler must beAnInstanceOf[JavaFxWindowHandler]
    }
    "call platformImplementation.startup" in {
      val startupCalled = WaitingBoolean(false)

      val launcher =
        new JavaFxApplicationLauncher {
          override val platformImplementation = new DefaultPlatformImplementation {
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
    "call platformImplementation.onIdle.observe" in {

      var observeCalled = false

      val signalSpy = new Signal {
        override def observe(observer: => Unit) = {
          observeCalled = true
          super.observe(observer)
        }
      }

      val launcher =
        new JavaFxApplicationLauncher {
          override val platformImplementation = new DefaultPlatformImplementation {
            override val onIdle = signalSpy
          }
          def createApplication() = new Application with Engine {
            def start(window: Window) = {}
          }
        }

      startAndExitApplication(launcher)

      observeCalled
    }
    "call application start on the JavaFx application thread" in {
      var correctThread = false
      val launcher =
        new JavaFxApplicationLauncher {
          def createApplication() = new Application with Engine {
            def start(window: Window) = {
              correctThread = toolkit.isFxUserThread
            }
          }
        }

      startAndExitApplication(launcher, 1.seconds)

      correctThread
    }
    "call application stop on the JavaFx application thread" in {
      var correctThread = false
      val launcher =
        new JavaFxApplicationLauncher {
          def createApplication() = new Application with Engine {
            def start(window: Window) = {}

            override def stop() = correctThread = toolkit.isFxUserThread
          }
        }

      startAndExitApplication(launcher, 1.seconds)

      correctThread
    }
    /*
    "call exit when the application has hidden it's last window" in {
      val exitCalled = WaitingBoolean(false)

      val launcher =
        new JavaFxApplicationLauncher {
          override def exit(application: Application) = {
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

      start(launcher)

      exitCalled.get
    }
     */
    "exit the application if the signal comes from outside" in {
      //pretend a window was closed by the user
      val exitCalled = WaitingBoolean(false)

      val launcher =
        new JavaFxApplicationLauncher {
          override def exit(application: Application) = {
            super.exit(application)
            exitCalled set true
          }

          def createApplication() = new Application with Engine {
            def start(window: Window) = {
              show(window)
            }
          }
        }

      start(launcher)

      toolkit.notifyWindowListeners(List.empty[TKStage].asJava)

      exitCalled.get
    }
    "exit the platform on exit" in {
      val exitCalled = WaitingBoolean(false, 1.seconds)

      val launcher =
        new JavaFxApplicationLauncher {
          override val platformImplementation = new DefaultPlatformImplementation {
            override def exit() = {
              exitCalled set true
              super.exit()
            }
          }
          def createApplication() = new Application with Engine {
            def start(window: Window) = {
              exit()
            }
          }
        }

      start(launcher, 1.seconds)

      exitCalled.get
    }
  }
}