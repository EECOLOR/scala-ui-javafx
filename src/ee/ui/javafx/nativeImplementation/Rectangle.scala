package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.application.Toolkit
import ee.ui.properties.PropertyChangeCollector
import ee.ui.properties.PropertyChangeCollector._

class Rectangle(override val implemented:ee.ui.nativeElements.Rectangle) extends Shape(implemented) with Toolkit {
	val internalNode = toolkit.createPGRectangle
	
	override def update = {
	  super.update
	  propertyChanges.applyChanges
	}
	
	@inline implicit def doubleToFloat(d:Double) = d.toFloat
	
	val propertyChanges = (
		implemented.x,
		implemented.y,
		implemented.width,
		implemented.height,
		implemented.arcWidth,
		implemented.arcHeight
	) ~> (internalNode updateRectangle (_, _, _, _, _, _))
	
}