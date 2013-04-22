package ee.ui.display.shapes

import ee.ui.display.implementation.contracts.TextContract
import com.sun.javafx.sg.PGText
import ee.ui.implementation.Toolkit._
import ee.ui.display.JavaFxShape
import javafx.scene.text.{TextAlignment => JavaFxTextAlignment}
import ee.ui.display.shapes.detail.TextAlignment
import scala.language.implicitConversions
import javafx.scene.text.Font
import ee.ui.display.primitives.Color
import ee.ui.members.ReadOnlyProperty
import ee.ui.system.RestrictedAccess
import com.sun.javafx.geom.RectBounds
import javafx.geometry.VPos
import javafx.scene.text.TextBoundsType
import javafx.scene.text.FontSmoothingType
import ee.ui.implementation.Converter
import ee.ui.primitives.VerticalPosition

class JavaFxText(text:TextContract, override val internalNode: PGText = toolkit.createPGText()) extends 
  JavaFxShape(text, internalNode) {

  val helper = internalNode.getTextHelper
  
  val bindToText:Unit = {
    text.text bindWith helper.setText
    text.textAlignment bindWith (helper setTextAlignment _)
    text.textOrigin bindWith (helper setTextOrigin _)
   
    (text.text.change | text.textAlignment.change | text.textOrigin.change) {
      internalNode.updateText()
      boundsTransformed(text.bounds)
      dirty = true
    }
    
    internalNode.updateText()
  }
  
  implicit def textAlignmentToInt(textAlignment: TextAlignment): Int =
    (textAlignment match {
      case TextAlignment.LEFT => JavaFxTextAlignment.LEFT
      case TextAlignment.CENTER => JavaFxTextAlignment.CENTER
      case TextAlignment.JUSTIFY => JavaFxTextAlignment.JUSTIFY
      case TextAlignment.RIGHT => JavaFxTextAlignment.RIGHT
    }).ordinal
    
  implicit def textOriginToInt(textOrigin: VerticalPosition): Int =
    (textOrigin match {
      case VerticalPosition.TOP => VPos.TOP
      case VerticalPosition.CENTER => VPos.CENTER
      case VerticalPosition.BOTTOM => VPos.BOTTOM
    }).ordinal
}