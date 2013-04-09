package ee.ui.implementation

import ee.ui.display.Window
import scala.collection.mutable
import ee.ui.display.JavaFxWindow
import ee.ui.display.JavaFxWindow
import ee.ui.implementation.contracts.WindowContract
import ee.ui.implementation.contracts.WindowContract
import ee.ui.implementation.contracts.WindowContract

class DefaultWindowImplementationHandler extends WindowImplementationHandler {
  val createWindow:WindowContract => JavaFxWindow = JavaFxWindow
  
  def hide(windowContract: WindowContract): Unit = {
    this(windowContract).hide()
    windowContracts -= windowContract
  }
  def show(windowContract: WindowContract): Unit = this(windowContract).show()
  
  lazy val windowContracts = 
    mutable.Map.empty[WindowContract, JavaFxWindow].withDefault(createWindow)
  
  def apply(windowContract:WindowContract) = 
    windowContracts.getOrElseUpdate(windowContract, windowContracts(windowContract))
}