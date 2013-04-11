package ee.ui.implementation

import ee.ui.display.Window
import scala.collection.mutable
import ee.ui.display.JavaFxWindow
import ee.ui.display.JavaFxWindow
import ee.ui.implementation.contracts.WindowContract
import ee.ui.implementation.contracts.WindowContract
import ee.ui.implementation.contracts.WindowContract

class DefaultWindowImplementationHandler extends WindowImplementationHandler with ContractHandler {
  type ContractType = WindowContract
  type ImplementationType = JavaFxWindow
  
  val create:WindowContract => JavaFxWindow = JavaFxWindow
  
  def hide(windowContract: WindowContract): Unit = {
    this(windowContract).hide()
    contracts -= windowContract
  }
  def show(windowContract: WindowContract): Unit = this(windowContract).show()
}