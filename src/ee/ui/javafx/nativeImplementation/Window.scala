package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.application.Toolkit
import com.sun.javafx.tk.TKStage
import ee.ui.nativeImplementation.NativeImplementation

class Window(val implemented:ee.ui.nativeElements.Window) extends NativeImplementation with Toolkit {
    def internalStage:Option[TKStage] = None
    
    def init = {}
 }