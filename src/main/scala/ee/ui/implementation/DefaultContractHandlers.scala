package ee.ui.implementation

class DefaultContractHandlers extends ContractHandlers { 
  implicit val contractHandlers = this
  
  val windows = new JavaFxWindowHandler
  val scenes = new JavaFxSceneHandler
  val nodes = new JavaFxNodeHandler
}