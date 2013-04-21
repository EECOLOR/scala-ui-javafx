package test.toolkit

import com.sun.javafx.tk.TKScene
import com.sun.javafx.sg.PGNode
import com.sun.javafx.geom.CameraImpl
import com.sun.javafx.tk.TKSceneListener
import com.sun.javafx.tk.TKScenePaintListener

class StubScene extends TKScene {

  var sceneListener: TKSceneListener = _
  var paintListener: TKScenePaintListener = _

  def setRoot(root: PGNode): Unit = {}
  def setCamera(camera: CameraImpl): Unit = {}
  def setTKSceneListener(listener: TKSceneListener): Unit = sceneListener = listener
  def markDirty(): Unit = {}
  def setTKScenePaintListener(listener: TKScenePaintListener): Unit = paintListener = listener

  def computePickRay(x$1: Float, x$2: Float, x$3: com.sun.javafx.geom.PickRay): com.sun.javafx.geom.PickRay = ???
  def createDragboard(): javafx.scene.input.Dragboard = ???
  def enableInputMethodEvents(x$1: Boolean): Unit = ???
  def entireSceneNeedsRepaint(): Unit = ???
  def requestFocus(): Unit = ???
  def setCursor(x$1: Any): Unit = ???
  def setFillPaint(x$1: Any): Unit = ???
  def setScene(x$1: Any): Unit = ???

}