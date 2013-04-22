package test.toolkit

import com.sun.javafx.sg.PGTextHelper

class StubTextHelper extends PGTextHelper {

  def setText(text: String): Unit = {}
  def setTextAlignment(textAlignmentOrdinal: Int): Unit = {}
  def setTextOrigin(verticalPositionOrdinal: Int): Unit = {}

  def setFont(fontImplementation: Any): Unit = ???
  def computeContains(x$1: Float, x$2: Float): Boolean = ???
  def computeContentBounds(x$1: com.sun.javafx.geom.BaseBounds, x$2: com.sun.javafx.geom.transform.BaseTransform): com.sun.javafx.geom.BaseBounds = ???
  def computeLayoutBounds(x$1: com.sun.javafx.geom.BaseBounds): com.sun.javafx.geom.BaseBounds = ???
  def getCaretShape(x$1: Int, x$2: Boolean): Object = ???
  def getHitInfo(x$1: Float, x$2: Float): Object = ???
  def getRangeShape(x$1: Int, x$2: Int): Object = ???
  def getSelectionShape(): Object = ???
  def getShape(): Object = ???
  def getUnderlineShape(x$1: Int, x$2: Int): Object = ???
  def setCumulativeTransform(x$1: com.sun.javafx.geom.transform.BaseTransform): Unit = ???
  def setFontSmoothingType(x$1: Int): Unit = ???
  def setLocation(x$1: Float, x$2: Float): Unit = ???
  def setLogicalSelection(x$1: Int, x$2: Int): Unit = ???
  def setMode(x$1: com.sun.javafx.sg.PGShape.Mode): Unit = ???
  def setSelectionPaint(x$1: Any, x$2: Any): Unit = ???
  def setStrikethrough(x$1: Boolean): Unit = ???
  def setStroke(x$1: Boolean): Unit = ???
  def setStrokeParameters(x$1: com.sun.javafx.sg.PGShape.StrokeType, x$2: Array[Float], x$3: Float, x$4: com.sun.javafx.sg.PGShape.StrokeLineCap, x$5: com.sun.javafx.sg.PGShape.StrokeLineJoin, x$6: Float, x$7: Float): Unit = ???
  def setTextBoundsType(x$1: Int): Unit = ???
  def setUnderline(x$1: Boolean): Unit = ???
  def setWrappingWidth(x$1: Float): Unit = ???

}