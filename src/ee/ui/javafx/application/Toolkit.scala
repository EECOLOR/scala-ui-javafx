package ee.ui.javafx.application

import com.sun.javafx.tk.{Toolkit => JavaFxToolkit}

trait Toolkit {
	def toolkit = JavaFxToolkit getToolkit
}