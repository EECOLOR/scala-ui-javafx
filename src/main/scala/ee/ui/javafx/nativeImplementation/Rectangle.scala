package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.application.Toolkit
import ee.ui.properties.PropertyGroup._
import scala.language.implicitConversions
import scala.language.existentials

class Rectangle(override val implemented:ee.ui.display.shape.Rectangle) extends Shape(implemented) with Toolkit {
	val internalNode = toolkit.createPGRectangle
	
	override def update = {
	  super.update
	  propertyChanges.applyIfChanged
	}
	
	@inline implicit def doubleToFloat(d:Double) = d.toFloat
	
	val propertyChanges = (
		implemented.width,
		implemented.height,
		implemented.arcWidth,
		implemented.arcHeight
	) ~~> (internalNode updateRectangle (0, 0, _, _, _, _))
	propertyChanges.changed = true
}