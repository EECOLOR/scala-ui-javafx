package ee.ui.display

import ee.ui.implementation.contracts.SceneContract
import ee.ui.implementation.ContractHandlers
import ee.ui.display.implementation.contracts.NodeContract
import com.sun.javafx.sg.PGNode
import com.sun.javafx.tk.TKSceneListener
import ee.ui.system.RestrictedAccess
import com.sun.javafx.tk.Toolkit
import com.sun.javafx.tk.TKScenePaintListener

case class JavaFxScene(owner: JavaFxWindow, scene: SceneContract)(implicit contractHandlers: ContractHandlers) {

  val internalScene = owner.internalWindow createTKScene false

  object internalSceneListener extends TKSceneListener {
    
    def changedSize(width: Float, height: Float): Unit = {
        implicit val restrictedAccess = RestrictedAccess
        scene.width = width
        scene.height = height
    }
    
    def changedLocation(x: Float, y: Float): Unit = println("JavaFxScene - implement changedLocation", x, y)
    
    def inputMethodEvent(x$1: Any): Unit = ???
    def keyEvent(x$1: Any): Unit = ???
    def menuEvent(x$1: Double, x$2: Double, x$3: Double, x$4: Double, x$5: Boolean): Unit = ???
    def mouseEvent(x$1: Any): Unit = ???
    def rotateEvent(x$1: javafx.event.EventType[javafx.scene.input.RotateEvent], x$2: Double, x$3: Double, x$4: Double, x$5: Double, x$6: Double, x$7: Double, x$8: Boolean, x$9: Boolean, x$10: Boolean, x$11: Boolean, x$12: Boolean, x$13: Boolean): Unit = ???
    def scrollEvent(x$1: javafx.event.EventType[javafx.scene.input.ScrollEvent], x$2: Double, x$3: Double, x$4: Double, x$5: Double, x$6: Double, x$7: Double, x$8: Int, x$9: Int, x$10: Int, x$11: Int, x$12: Int, x$13: Double, x$14: Double, x$15: Double, x$16: Double, x$17: Boolean, x$18: Boolean, x$19: Boolean, x$20: Boolean, x$21: Boolean, x$22: Boolean): Unit = ???
    def swipeEvent(x$1: javafx.event.EventType[javafx.scene.input.SwipeEvent], x$2: Int, x$3: Double, x$4: Double, x$5: Double, x$6: Double, x$7: Boolean, x$8: Boolean, x$9: Boolean, x$10: Boolean, x$11: Boolean): Unit = ???
    def touchEventBegin(x$1: Long, x$2: Int, x$3: Boolean, x$4: Boolean, x$5: Boolean, x$6: Boolean, x$7: Boolean): Unit = ???
    def touchEventEnd(): Unit = ???
    def touchEventNext(x$1: javafx.scene.input.TouchPoint.State, x$2: Long, x$3: Int, x$4: Int, x$5: Int, x$6: Int): Unit = ???
    def zoomEvent(x$1: javafx.event.EventType[javafx.scene.input.ZoomEvent], x$2: Double, x$3: Double, x$4: Double, x$5: Double, x$6: Double, x$7: Double, x$8: Boolean, x$9: Boolean, x$10: Boolean, x$11: Boolean, x$12: Boolean, x$13: Boolean): Unit = ???

  }

  def setRoot(node:NodeContract):Unit = {
    val javaFxNode = contractHandlers.nodes(node)
    javaFxNode.dirty {
      internalScene.markDirty()
    }
    internalScene setRoot javaFxNode.internalNode
  }
  
  val bindToScene: Unit = {

    internalScene setTKSceneListener internalSceneListener
    internalScene setCamera null

    scene.root foreach setRoot
  }
}