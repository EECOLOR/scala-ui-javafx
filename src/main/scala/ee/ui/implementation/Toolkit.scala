package ee.ui.implementation

import com.sun.javafx.tk.{Toolkit => JavaFxToolkit}

trait Toolkit {
  def toolkit = JavaFxToolkit.getToolkit
}

object Toolkit extends Toolkit