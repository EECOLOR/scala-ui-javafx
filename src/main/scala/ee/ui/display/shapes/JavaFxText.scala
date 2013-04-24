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
import ee.ui.implementation.Toolkit

class JavaFxText(text:TextContract, override val internalNode: PGText = toolkit.createPGText()) extends 
  JavaFxShape(text, internalNode) with Toolkit {

  val layout = toolkit.getTextLayoutFactory.createLayout
  
  def font = Font.getDefault.impl_getNativeFont
  def setText(text:String) = layout.setContent(text, font)
  def getGlyphs() = layout.getRuns.asInstanceOf[Array[Object]]
  
  val bindToText:Unit = {
    
    text.text bindWith setText
    text.textAlignment bindWith (layout setAlignment _)
   
    (text.text.change | text.textAlignment.change) {
      boundsTransformed(text.bounds)
      internalNode.setGlyphs(getGlyphs)
      dirty = true
    }
    
    internalNode.setFont(font)
    internalNode.setGlyphs(getGlyphs)
  }
  
  implicit def textAlignmentToInt(textAlignment: TextAlignment): Int =
    (textAlignment match {
      case TextAlignment.LEFT => JavaFxTextAlignment.LEFT
      case TextAlignment.CENTER => JavaFxTextAlignment.CENTER
      case TextAlignment.JUSTIFY => JavaFxTextAlignment.JUSTIFY
      case TextAlignment.RIGHT => JavaFxTextAlignment.RIGHT
    }).ordinal
    
}