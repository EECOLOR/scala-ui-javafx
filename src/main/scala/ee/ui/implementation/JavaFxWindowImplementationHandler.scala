package ee.ui.implementation

import ee.ui.display.Window
import scala.collection.mutable
import ee.ui.display.JavaFxWindow
import ee.ui.display.JavaFxWindow
import ee.ui.implementation.contracts.WindowContract
import ee.ui.implementation.contracts.WindowContract
import ee.ui.implementation.contracts.WindowContract

class JavaFxWindowImplementationHandler(implicit contractHandlers:ContractHandlers) extends WindowImplementationHandler with ContractHandler[WindowContract, JavaFxWindow] {
  
  val create:WindowContract => JavaFxWindow = JavaFxWindow.apply
  
  def hide(windowContract: WindowContract): Unit = {
    (JavaFxWindowImplementationHandler.this(windowContract)).hide()
    contracts -= windowContract
  }
  def show(windowContract: WindowContract): Unit = (JavaFxWindowImplementationHandler.this(windowContract)).show()
}