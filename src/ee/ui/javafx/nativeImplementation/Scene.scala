package ee.ui.javafx.nativeImplementation

import ee.ui.nativeImplementation.NativeImplementation
import com.sun.javafx.tk.TKScene

class Scene(val implemented:ee.ui.nativeElements.Scene) extends NativeImplementation {
	def init = {}
	
	def initInternalScene(internalScene:TKScene) = {
	  
	}
	
	def disposeInternalScene = {
	  
	}
}