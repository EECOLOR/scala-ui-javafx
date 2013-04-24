package test.toolkit

import com.sun.javafx.scene.text.TextLayout
import com.sun.javafx.scene.text.GlyphList

class StubTextLayout extends TextLayout {
  
  def setContent(text: String, font: Any): Boolean = true
  def setAlignment(alignmentOrdinal: Int): Boolean = true
  def getRuns(): Array[GlyphList] = Array.empty
  
  def getBounds(x$1: com.sun.javafx.scene.text.TextSpan, x$2: com.sun.javafx.geom.BaseBounds): com.sun.javafx.geom.BaseBounds = ???
  def getBounds(): com.sun.javafx.geom.BaseBounds = ???
  def getCaretShape(x$1: Int, x$2: Boolean, x$3: Float, x$4: Float): Array[javafx.scene.shape.PathElement] = ???
  def getHitInfo(x$1: Float, x$2: Float): com.sun.javafx.scene.text.HitInfo = ???
  def getLines(): Array[com.sun.javafx.scene.text.TextLine] = ???
  def getRange(x$1: Int, x$2: Int, x$3: Int, x$4: Float, x$5: Float): Array[javafx.scene.shape.PathElement] = ???
  def getShape(x$1: Int, x$2: com.sun.javafx.scene.text.TextSpan): com.sun.javafx.geom.Shape = ???
  def setBoundsType(x$1: Int): Boolean = ???
  def setContent(x$1: Array[com.sun.javafx.scene.text.TextSpan]): Boolean = ???
  def setDirection(x$1: Int): Boolean = ???
  def setLineSpacing(x$1: Float): Boolean = ???
  def setWrapWidth(x$1: Float): Boolean = ???

}