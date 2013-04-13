package ee.ui.display

import ee.ui.implementation.contracts.SceneContract
import ee.ui.implementation.ContractHandlers
import ee.ui.display.implementation.contracts.NodeContract
import com.sun.javafx.sg.PGNode

case class JavaFxScene(owner:JavaFxWindow, scene:SceneContract)(implicit contractHandlers:ContractHandlers) {

  val getJavaFxNode = contractHandlers.nodes 
       
  
  lazy val internalScene = {
    val internalScene = owner.internalWindow createTKScene false
    
    scene.root foreach ( getJavaFxNode andThen (_.internalNode) andThen internalScene.setRoot)
    
    internalScene
  }
  
}