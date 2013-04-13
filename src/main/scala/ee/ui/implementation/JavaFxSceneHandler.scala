package ee.ui.implementation

import ee.ui.display.JavaFxWindow
import ee.ui.display.JavaFxScene
import ee.ui.implementation.contracts.SceneContract

class JavaFxSceneHandler(implicit contractHandlers:ContractHandlers) extends ContractHandler[(JavaFxWindow, SceneContract), JavaFxScene] {
  val create = (JavaFxScene.apply _).tupled
}