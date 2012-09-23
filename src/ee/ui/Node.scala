package ee.ui.javafx

import ee.ui.nativeImplementation.NativeImplementation
import com.sun.javafx.sg.PGNode

class Node(val implemented:ee.ui.Node) extends NativeImplementation {
	def init = {}
	
	def internalNode:PGNode
}