package ee.ui.application

import ee.ui.implementation.EngineImplementationContract
import ee.ui.implementation.WindowImplementationHandler
import ee.ui.implementation.ExitHandler
import ee.ui.implementation.JavaFxWindowImplementationHandler
import ee.ui.implementation.DefaultContractHandlers

abstract class JavaFxApplicationLauncher extends ApplicationLauncher with JavaFxLauncher {
  
  val platformImplementation:PlatformImplementation = new DefaultPlatformImplementation

  type Engine = JavaFxEngine

  val contractHandlers = new DefaultContractHandlers
  
  trait JavaFxEngine extends EngineImplementationContract {
    
    val windowImplementationHandler = contractHandlers.windows
    
    val exitHandler = JavaFxApplicationLauncher.this
    
    val settings = new ApplicationSettings
  }
}