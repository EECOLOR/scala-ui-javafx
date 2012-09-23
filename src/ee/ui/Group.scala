package ee.ui.javafx

import ee.ui.nativeImplementation.NativeImplementation
import ee.ui.javafx.application.Toolkit

class Group(val implemented:ee.ui.Group) extends Node(implemented) with NativeImplementation with Toolkit {
	def init = {}
	
	def internalNode = toolkit createPGGroup
}