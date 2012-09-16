package ee.ui.javafx.nativeImplementation

import ee.ui.nativeElements
import ee.ui.nativeImplementation.NativeManager

trait NativeManagerDependencies extends ee.ui.nativeImplementation.NativeManagerDependencies {

  implicit def windowManager = WindowManager
  implicit def stageManager = StageManager
  implicit def sceneManager = SceneManager
}

object NativeManagerDependencies extends NativeManagerDependencies

object WindowManager extends NativeManager[nativeElements.Window, Window] {
  protected def createInstance(element: nativeElements.Window) = new Window(element)
}

object StageManager extends NativeManager[nativeElements.Stage, Stage] {
  protected def createInstance(element: nativeElements.Stage) = new Stage(element)
}

object SceneManager extends NativeManager[nativeElements.Scene, Scene] {
	protected def createInstance(element: nativeElements.Scene) = new Scene(element)
}