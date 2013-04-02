package ee.ui.application

import ee.ui.implementation.EngineImplementationContract
import ee.ui.implementation.WindowImplementationHandler
import ee.ui.implementation.ExitHandler
import ee.ui.implementation.DefaultWindowImplementationHandler

abstract class JavaFxApplicationLauncher extends ApplicationLauncher with JavaFxLauncher {
  
  val platformImplementation:PlatformImplementation = new DefaultPlatformImplementation

  type Engine = JavaFxEngine

  trait JavaFxEngine extends EngineImplementationContract {
    
    val windowImplementationHandler = new DefaultWindowImplementationHandler
    
    val exitHandler = JavaFxApplicationLauncher.this
    
    val settings = new ApplicationSettings(application)
  }
}