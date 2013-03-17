package ee.ui.javafx.application

import org.specs2.Specification
import ee.ui.application.Application
import ee.ui.display.implementation.WindowImplementationHandler
import ee.ui.system.Platform
import ee.ui.display.Window
import ee.ui.members.Property
import javafx.application.ConditionalFeature

object LauncherSpecification extends Specification {

  trait TestApplication extends Application {
    //prevent start(window) from being called
    final override def start(): Unit = {
      internalStart()
      // pretend the platform is closed from the outside
      DefaultPlatformImplementation.exit()
    }
    def internalStart(): Unit

    def start(window: Window): Unit = ???
    implicit protected def platform: Platform = ???
    implicit protected def windowImplementationHandler: WindowImplementationHandler = ???
  }

  def is = "LauncherSpecification should".title ^
    "Support default application flow, (init, start, launchComplete, stop)" ! {
      val application = Property[Option[Application]](None)

      val launcher = new Launcher(DefaultPlatformImplementation)
      application <== launcher.launchComplete

      var startCalled = false
      var initCalled = false
      var stopCalled = false

      val testApplication = new TestApplication {
        def internalStart() = startCalled = true
        override def init() = initCalled = true
        override def stop() = stopCalled = true
      }

      def createApplication() = testApplication

      launcher.launch(Array.empty[String])(createApplication)

      (application.value ==== Some(testApplication)) and
        (startCalled === true) and
        (initCalled === true) and
        (stopCalled === true)
    } ^ "Call the methods in the correct threads" ! {
      
      val launcher = new Launcher(DefaultPlatformImplementation)

      var initThreadName = ""
      var startThreadName = ""
      var stopThreadName = ""

      val testApplication = new TestApplication {
        override def init() = initThreadName = Thread.currentThread.getName
        def internalStart() = startThreadName = Thread.currentThread.getName
        override def stop() = stopThreadName = Thread.currentThread.getName
      }

      var createApplicationThreadName = ""
      
      def createApplication() = {
        createApplicationThreadName = Thread.currentThread.getName
        testApplication
      }

      launcher.launch(Array.empty[String])(createApplication)
      
      (createApplicationThreadName === "JavaFX-Launcher") and
      (initThreadName === "JavaFX-Launcher") and
      (startThreadName === "JavaFX Application Thread") and
      (stopThreadName === "JavaFX Application Thread")
    } ^
    end
}