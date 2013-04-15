package ee.ui.implementation

import org.specs2.mock.Mockito

class SpyContractHandlers extends ContractHandlers with Mockito {
  implicit val contractHandlers = this
  
  val windows = spy(new JavaFxWindowHandler)
  val scenes = spy(new JavaFxSceneHandler)
  val nodes = spy(new JavaFxNodeHandler)
}