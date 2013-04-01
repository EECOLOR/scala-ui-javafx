package ee.ui.implementation

import ee.ui.display.Window
import scala.collection.mutable
import ee.ui.display.JavaFxWindow
import ee.ui.display.JavaFxWindow

class DefaultWindowImplementationHandler extends WindowImplementationHandler {
  val createWindow:Window => JavaFxWindow = JavaFxWindow
  
  def hide(window: Window): Unit = {
    this(window).hide()
    windows -= window
  }
  def show(window: Window): Unit = this(window).show()
  
  lazy val windows = mutable.Map.empty[Window, JavaFxWindow].withDefault(createWindow)
  
  def apply(window:Window) = windows.getOrElseUpdate(window, windows(window))
}