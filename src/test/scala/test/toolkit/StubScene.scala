package test.toolkit

import com.sun.javafx.tk.TKScene
import com.sun.javafx.sg.PGNode
import com.sun.javafx.geom.CameraImpl

class StubScene extends TKScene {
  
  def setRoot(root:PGNode): Unit = {}
  def setCamera(camera: CameraImpl): Unit = {}
  
  def computePickRay(x$1: Float, x$2: Float, x$3: com.sun.javafx.geom.PickRay): com.sun.javafx.geom.PickRay = ???
  def createDragboard(): javafx.scene.input.Dragboard = ???
  def enableInputMethodEvents(x$1: Boolean): Unit = ???
  def entireSceneNeedsRepaint(): Unit = ???
  def markDirty(): Unit = ???
  def requestFocus(): Unit = ???
  def setCursor(x$1: Any): Unit = ???
  def setFillPaint(x$1: Any): Unit = ???
  def setScene(x$1: Any): Unit = ???
  def setTKSceneListener(x$1: com.sun.javafx.tk.TKSceneListener): Unit = ???
  def setTKScenePaintListener(x$1: com.sun.javafx.tk.TKScenePaintListener): Unit = ???

}