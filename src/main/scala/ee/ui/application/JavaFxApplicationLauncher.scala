package ee.ui.application

import ee.ui.implementation.EngineImplementationContract
import ee.ui.implementation.WindowImplementationHandler
import ee.ui.implementation.ExitHandler

abstract class JavaFxApplicationLauncher extends ApplicationLauncher with JavaFxLauncher {
  
  val platformImplementation:PlatformImplementation = DefaultPlatformImplementation

  type Engine = JavaFxEngine

  trait JavaFxEngine extends EngineImplementationContract {
    
    val windowImplementationHandler = new WindowImplementationHandler {
      def hide(window: ee.ui.display.Window): Unit = ???
      def show(window: ee.ui.display.Window): Unit = ???
    }
    
    val exitHandler = new ExitHandler {
      def exit(application:Application):Unit = JavaFxApplicationLauncher.this.exit(application)
    }
    
    val settings = new ApplicationSettings(application)
  }
}