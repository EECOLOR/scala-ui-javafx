package ee.ui.javafx

import ee.ui.nativeImplementation.NativeImplementation
import ee.ui.javafx.application.Toolkit

class Group(val implemented:ee.ui.Group) extends Node with NativeImplementation with Toolkit {
	def init = {}
	
	val internalNode = toolkit createPGGroup
}