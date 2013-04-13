package ee.ui.implementation

import ee.ui.implementation.contracts.SceneContract
import ee.ui.display.JavaFxScene

class DefaultContractHandlers extends ContractHandlers { 
  implicit val contractHandlers = this
  
  val windows = new JavaFxWindowImplementationHandler
  val scenes =
    new ContractHandler[SceneContract, JavaFxScene] {
      val create = JavaFxScene 
    }
}