package ee.ui.implementation

import ee.ui.implementation.contracts.SceneContract
import ee.ui.display.JavaFxScene
import ee.ui.display.JavaFxWindow
import ee.ui.display.JavaFxScene
import utils.SubtypeTest
import org.specs2.mutable.Specification

class JavaFxSceneHandlerTest extends Specification {
  
  xonly
  
  "JavaFxSceneHandler" should {
    
    "extend the correct type" in {
      SubtypeTest[JavaFxSceneHandler <:< ContractHandler[(JavaFxWindow, SceneContract), JavaFxScene]]
    }
  }
}