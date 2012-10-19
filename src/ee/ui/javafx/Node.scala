package ee.ui.javafx

import com.sun.javafx.sg.PGNode
import ee.ui.javafx.nativeImplementation.NativeImplementation

abstract class Node extends NativeImplementation {
	
	def internalNode:PGNode
}