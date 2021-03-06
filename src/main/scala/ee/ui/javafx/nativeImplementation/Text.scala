package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.application.Toolkit
import javafx.scene.text.{ TextBoundsType => FxTextBoundsType }
import ee.ui.primitives.VerticalPosition
import javafx.geometry.VPos
import javafx.scene.text.{ TextAlignment => FxTextAlignment }
import javafx.scene.text.{ FontSmoothingType => FxFontSmoothingType }
import ee.ui.primitives.Font
import com.sun.javafx.geom.RectBounds
import ee.ui.system.RestrictedAccess
import ee.ui.display.FontSmoothingType
import ee.ui.display.TextBoundsType
import ee.ui.display.TextAlignment
import scala.language.implicitConversions

class Text(override val implemented: ee.ui.display.Text) extends Shape(implemented) with Toolkit {
  
  val internalNode = toolkit.createPGText

  updateImplementation(internalNode.updateText)
  
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
  
  //TODO create TextContract
  
    //implemented.resizable.change foreach (internalStage setResizable _)
    implemented.boundsType change (helper setTextBoundsType _)
    implemented.textOrigin change (helper setTextOrigin _)
    implemented.wrappingWidth change (helper setWrappingWidth _)
    implemented.underline change (helper setUnderline _)
    implemented.strikethrough change (helper setStrikethrough _)
    implemented.textAlignment change (helper setTextAlignment _)
    implemented.fontSmoothingType change (helper setFontSmoothingType _)
    implemented.font change (helper setFont getNativeFont(_))
    implemented.text change (helper setText _)
    implemented.stroke change (helper setStroke _.isDefined)
    //TODO we need to listen to more properties
    implemented.text change { text =>
      val bounds = 
        if (text.length == 0) new RectBounds
        else helper computeLayoutBounds new RectBounds
        
      implicit val access = RestrictedAccess
        
      implemented.width = bounds.getWidth
      implemented.height = bounds.getHeight
    }
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
                helper.setLogicalSelection(0, 0);	    */ 
}