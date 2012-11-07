package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.application.Toolkit
import ee.ui.properties.PropertyChangeCollector
import ee.ui.properties.PropertyGroup._
import ee.ui.nativeElements.TextBoundsType
import javafx.scene.text.{ TextBoundsType => FxTextBoundsType }
import ee.ui.primitives.VerticalPosition
import javafx.geometry.VPos
import ee.ui.nativeElements.TextAlignment
import javafx.scene.text.{ TextAlignment => FxTextAlignment }
import ee.ui.nativeElements.FontSmoothingType
import javafx.scene.text.{ FontSmoothingType => FxFontSmoothingType }
import ee.ui.primitives.Font

class Text(override val implemented: ee.ui.nativeElements.Text) extends Shape(implemented) with Toolkit {
  
  val internalNode = toolkit.createPGText

  override def update = {
    super.update
    
    propertyChanges.applyIfChanged
    internalNode.updateText
  }
  
  override val ignorePosition = true
  
  lazy val helper = internalNode.getTextHelper

  implicit def textBoundsTypeToInt(textBoundsType: TextBoundsType): Int =
    (textBoundsType match {
      case TextBoundsType.LOGICAL => FxTextBoundsType.LOGICAL
      case TextBoundsType.VISUAL => FxTextBoundsType.VISUAL
    }).ordinal

  implicit def textOriginToInt(textOrigin: VerticalPosition): Int =
    (textOrigin match {
      case VerticalPosition.TOP => VPos.TOP
      case VerticalPosition.CENTER => VPos.CENTER
      case VerticalPosition.BASELINE => VPos.BASELINE
      case VerticalPosition.BOTTOM => VPos.BOTTOM
    }).ordinal

  implicit def textAlignmentToInt(textAlignment: TextAlignment): Int =
    (textAlignment match {
      case TextAlignment.LEFT => FxTextAlignment.LEFT
      case TextAlignment.CENTER => FxTextAlignment.CENTER
      case TextAlignment.JUSTIFY => FxTextAlignment.JUSTIFY
      case TextAlignment.RIGHT => FxTextAlignment.RIGHT
    }).ordinal

  implicit def fontSmoothingTypeToInt(fontSmoothingType: FontSmoothingType): Int =
    (fontSmoothingType match {
      case FontSmoothingType.GRAY => FxFontSmoothingType.GRAY
      case FontSmoothingType.LCD => FxFontSmoothingType.LCD
    }).ordinal

  def getNativeFont(font: Font): AnyRef = {
    val javaFxFont = Converters convertFont font

    /*
     * Bad implementation on the JavaFxSide, there is no 
     * method to retrieve an internal font. It's implemented 
     * as some sort of callback
     */
    javaFxFont.impl_getNativeFont
  }

  @inline implicit def doubleToFloat(d:Double) = d.toFloat
  
  val propertyChanges = new PropertyChangeCollector(
    //implemented.resizable ~~> (internalStage setResizable _)
    (implemented.x, implemented.y) ~~> (helper setLocation (_, _)),
    implemented.boundsType ~~> (helper setTextBoundsType _),
    implemented.textOrigin ~~> (helper setTextOrigin _),
    implemented.wrappingWidth ~~> (helper setWrappingWidth _),
    implemented.underline ~~> (helper setUnderline _),
    implemented.strikethrough ~~> (helper setStrikethrough _),
    implemented.textAlignment ~~> (helper setTextAlignment _),
    implemented.fontSmoothingType ~~> (helper setFontSmoothingType _),
    implemented.font ~~> (helper setFont getNativeFont(_)),
    implemented.text ~~> (helper setText _),
    implemented.stroke ~~> (helper setStroke _.isDefined)
    /* TODO implement selection
      
        if (impl_isDirty(DirtyBits.TEXT_SELECTION)) {
            if (getImpl_selectionStart() >= 0 && getImpl_selectionEnd() >= 0) {
                helper.setLogicalSelection(getImpl_selectionStart(),
                                           getImpl_selectionEnd());
                // getStroke and getFill can be null
                Paint strokePaint = getStroke();
                Paint fillPaint =
                    selectionFill == null ? null : selectionFill.get();
                Object strokeObj = (strokePaint == null) ? null :
                    strokePaint.impl_getPlatformPaint();
                Object fillObj = (fillPaint == null) ? null :
                    fillPaint.impl_getPlatformPaint();

                helper.setSelectionPaint(strokeObj, fillObj);
            } else {
                // Deselect any PGText, in order to update selected text color
                helper.setLogicalSelection(0, 0);	    */ )
  propertyChanges.changed = true
}